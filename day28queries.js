use bgg

db.getCollection("games").find({})


db.games.find(
{
    gid: 2
}
)

db.comments.find(
{
    gid: 2
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



//not working
db.games.aggregate([
{
    $match: { gid: 2}
},
{
    $lookup: {
        from: 'comments',
        pipeline: [
            { $project: {
                $concat : [
                "/review/", "$c_id"
                ]
            }
                 }
        ],
        as: 'reviews'
    }
}])

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
])
