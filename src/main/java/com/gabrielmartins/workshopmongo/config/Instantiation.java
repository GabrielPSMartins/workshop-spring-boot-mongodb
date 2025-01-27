package com.gabrielmartins.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.gabrielmartins.workshopmongo.domain.Post;
import com.gabrielmartins.workshopmongo.domain.User;
import com.gabrielmartins.workshopmongo.dto.AuthorDTO;
import com.gabrielmartins.workshopmongo.dto.CommentDTO;
import com.gabrielmartins.workshopmongo.repository.PostRepository;
import com.gabrielmartins.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userReposiroty;
	
	@Autowired
	private PostRepository postReposiroty;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userReposiroty.deleteAll();
		postReposiroty.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userReposiroty.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("23/01/2025"), "Partiu estudar", "Vamos começar mais um dia de estudo. Boa sorte pra nós!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("25/01/2025"), "Vamos ler um pouco!", "Hoje estou com vontade de ler para relaxar.", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Bons estudos!", sdf.parse("26/01/2025"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Se esforce!", sdf.parse("26/01/2025"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Boa ideia!", sdf.parse("26/01/2025"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postReposiroty.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userReposiroty.save(maria);
	}
}
