package com.bragadev.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.bragadev.workshopmongo.domain.Post;
import com.bragadev.workshopmongo.domain.User;
import com.bragadev.workshopmongo.dto.AuthorDTO;
import com.bragadev.workshopmongo.dto.CommentDTO;
import com.bragadev.workshopmongo.repository.PostRepository;
import com.bragadev.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(
				null, 
				sdf.parse("21/03/2024"), 
				"Here we go!", 
				"I'm going to Germany! Take care!",
				new AuthorDTO(maria)
				);
		
		Post post2 = new Post(
				null, 
				sdf.parse("13/11/2024"), 
				"Where are you from?", 
				"Can you answer me?",
				new AuthorDTO(maria)
				);
		
		CommentDTO c1 = new CommentDTO(
				"Good adventure my friend",
				sdf.parse("22/03/2018"),
				new AuthorDTO(alex));
		
		CommentDTO c2 = new CommentDTO(
				"Have fun!",
				sdf.parse("22/03/2021"),
				new AuthorDTO(bob));
		
		CommentDTO c3 = new CommentDTO(
				"Have a nice day",
				sdf.parse("22/03/2020"),
				new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().addAll(Arrays.asList(c3));

		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
