use bgg

db.getCollection("games").find({
})


db.games.find(
{
    name: { $regex: "lord of the rings", $options: "i"}
}
)

db.comments.find(
{
    gid: 7467
}
)

//join tables, sort by nested attribute
db.games.aggregate([
{
    $match: { gid: 2}
},
{
    $lookup: {
        from: 'comments',
        foreignField: 'gid',
        localField: 'gid',
        pipeline: [
        { $sort: { rating: -1}}
        ],
        as: 'reviews'
    }
}
])


//nested object with nested array of c_id
db.games.aggregate([
{
    $match: { gid: 2}
},
{
    $lookup: {
        from: 'comments',
        foreignField: 'gid',
        localField: 'gid',
        as: 'reviews'
    }
},
{
        $replaceRoot : { newRoot : { $mergeObjects: ["$$ROOT" , { "reviews" : { c_id : "$reviews.c_id"}}]}
    }
}])

//nested array of strings
db.games.aggregate([
{
    $match: { gid: 2}
},
{
    $lookup: {
        from: 'comments',
        foreignField: 'gid',
        localField: 'gid',
        as: 'reviews'
    }
},
{
        $replaceRoot : { newRoot : { $mergeObjects: ["$$ROOT" , { 
            "reviews" : "$reviews.c_id"
            }]}
    }
}])


//to project nested array into single value array AND manipulate($map) each item in array
db.games.aggregate([
{
    $match: { gid: 5}
},
{
    $lookup: {
        from: 'comments',
        foreignField: 'gid',
        localField: 'gid',
        as: 'comments'
    }
}
,{ $project: { 
    _id: 0,
    gid: 1,
    name: 1,
    year: 1,
    rank: "$ranking",
    users_rated: 1,
    url: 1,
    thumbnail: "$image",
    reviews: { $map: {
        input: "$comments.c_id",
        as: "c_id",
        in: { $concat: ["/review/", "$$c_id"]}
        } }
//    reviews: "$comments.c_id" //for single value array
    }}
])

//sort then group to use $first, then lookup to join table
db.comments.aggregate([
{ $sort: {"rating": -1}},
{
    $group: {
        _id: "$gid",
        highestRating: { $first: "$rating"},
        user: { $first: "$user"},
        highestComment: { $first: "$c_text"},
        reviewId: { $first: "$c_id"}
//        rating: { $push: "$rating"} //to show all ratings
    }
},
{
    $lookup: {
        from: 'games',
        foreignField: 'gid',
        localField: '_id',
        as: 'game'
    }
}
//,{ $unwind: "$game"} // to change array to object
//,{ $replaceRoot: { newRoot: { $mergeObjects: [ {gameName: "$game.name"}, "$$ROOT"]}}} // to return single value from object to root level
,{ $replaceRoot: {newRoot: {$mergeObjects: [ {$arrayElemAt: ["$game", 0]}, "$$ROOT"]}}} // to return all values from first object in array to root level (no need to unwind)
,{ $limit : 5 }
,{ $project: {"game": 0}}
])


// -----------------------Insert, Update, Upsert------------------------------

db.comments.find( { 
//    _id: ObjectId("64b7d6be6d4381ca2c27cee5")
//    gid: 2
    c_id: 1234567
})

db.comments.insert(
{
    user: "test user",
    c_text: "test comments",
    c_id: 123456,
    gid: 2
}
)

db.comments.updateOne(
{
    _id: ObjectId("64b7d6be6d4381ca2c27cee5")
},
{
    $currentDate : {timestamp: true },
    $max: {rating: 1} 
}
)

db.comments.update(
{
    c_id: 1234567
},{
    $currentDate: {timestamp: true},
    $set: {rating: 5},
    $set: {gid: 2}
}
,{ upsert: true}    
)
