package practice.day28workshop;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import practice.day28workshop.repository.CommentRepository;
import practice.day28workshop.repository.GameRepository;

@SpringBootApplication
public class Day28workshopApplication implements CommandLineRunner{

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private CommentRepository commentRepo;
	public static void main(String[] args) {
		SpringApplication.run(Day28workshopApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		List<Document> result = commentRepo.getGamesSortedByRating(true);
		System.out.println(result.get(0).toString());
		// for (Document d : result){
		// 	System.out.println(d.toString());
		// }
	}

}
