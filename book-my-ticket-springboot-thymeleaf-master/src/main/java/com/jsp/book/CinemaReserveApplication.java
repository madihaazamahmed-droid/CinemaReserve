package com.jsp.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.jsp.book.repository.MovieRepository;
import com.jsp.book.repository.TheaterRepository;
import com.jsp.book.entity.Movie;
import com.jsp.book.entity.Theater;
import java.util.List;

@SpringBootApplication
public class CinemaReserveApplication {
	public static void main(String[] args) {
		SpringApplication.run(CinemaReserveApplication.class, args);
	}

	@Bean
	CommandLineRunner initializeData(MovieRepository movieRepository, TheaterRepository theaterRepository) {
		return args -> {
			// --- MOVIE INITIALIZATION ---
			List<Movie> movies = movieRepository.findAll();
			
			if (movies.isEmpty()) {
				System.out.println(">>> Database is empty! Seeding sample movies...");
				
				Movie movie1 = new Movie();
				movie1.setName("Inception");
				movie1.setLanguages("English, Hindi");
				movie1.setGenre("Sci-Fi, Action");
				movie1.setDuration(java.time.LocalTime.of(2, 28));
				movie1.setImageLink("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg");
				movie1.setTrailerLink("https://www.youtube.com/watch?v=YoHD9XEInc0");
				movie1.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology...");
				movie1.setReleaseDate(java.time.LocalDate.of(2010, 7, 16));
				movie1.setCast("Leonardo DiCaprio, Joseph Gordon-Levitt");
				movieRepository.save(movie1);
				
				Movie movie2 = new Movie();
				movie2.setName("The Dark Knight");
				movie2.setLanguages("English");
				movie2.setGenre("Action, Crime");
				movie2.setDuration(java.time.LocalTime.of(2, 32));
				movie2.setImageLink("https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg");
				movie2.setTrailerLink("https://www.youtube.com/watch?v=EXeTwQWrcwY");
				movie2.setDescription("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham...");
				movie2.setReleaseDate(java.time.LocalDate.of(2008, 7, 18));
				movie2.setCast("Christian Bale, Heath Ledger");
				movieRepository.save(movie2);
				
				movies = movieRepository.findAll();
			}
			
			System.out.println(">>> Checking " + movies.size() + " movies for placeholders...");
			for (Movie movie : movies) {
				String name = movie.getName() != null ? movie.getName().toLowerCase() : "";
				String currentImg = movie.getImageLink();
				
				// More aggressive detection + Fix for previous unreliable links
				boolean isPlaceholder = currentImg == null || 
										currentImg.isBlank() || 
										currentImg.contains("600x400") || 
										currentImg.contains("placeholder") || 
										currentImg.contains("via.placeholder") ||
										currentImg.contains("tmdb") || // Replace unreliable TMDb links
										name.equals("rasheed") ||
										name.contains("batman");
				
				if (isPlaceholder) {
					System.out.println(">>> Updating placeholder for: " + movie.getName());
					if (name.contains("inception")) {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=YoHD9XEInc0");
					} else if (name.contains("avengers") || name.contains("endgame")) {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=TcMBFSGZo1E");
					} else if (name.contains("interstellar")) {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=zSWdZVtXT7E");
					} else if (name.contains("dark knight") || name.equals("rasheed") || name.contains("test")) {
						if (name.equals("rasheed") || name.contains("test")) movie.setName("The Dark Knight");
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=EXeTwQWrcwY");
					} else if (name.contains("avatar")) {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BNjA3NGExZDktNDlhZC00NjYyLTgwNmUtZWUzMDYwMTZjOTAxXkEyXkFqcGdeQXVyMTU5OTA4NTIz._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=5PSNL1qE6VY");
					} else if (name.contains("joker")) {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiY2JlYmI3MzE3XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=zAGVQLHvwOY");
					} else if (name.contains("batman")) {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BM2MyNTAwZGEtNTAxNC00ODVjLTgzZjUtYmU0YjZlMzFlNjZhXkEyXkFqcGdeQXVyNDc2NTg3NzA@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=mqqft2x_Aa4");
					} else {
						movie.setImageLink("https://m.media-amazon.com/images/M/MV5BMjA4NjA3OTM4NF5BMl5BanBnXkFtZTcwMTM0ODgyNw@@._V1_.jpg");
						movie.setTrailerLink("https://www.youtube.com/watch?v=rARN6agiW7w");
					}
					System.out.println(">>> Set Image URL (IMDb): " + movie.getImageLink());
					movieRepository.save(movie);
				}
			}

			// --- THEATER INITIALIZATION ---
			List<Theater> theaters = theaterRepository.findAll();
			System.out.println(">>> Checking " + theaters.size() + " theaters for placeholders...");
			String[] theaterImages = {
				"https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?q=80&w=1000&auto=format&fit=crop",
				"https://images.unsplash.com/photo-1517604401157-538a9663ecf3?q=80&w=1000&auto=format&fit=crop",
				"https://images.unsplash.com/photo-1505686994434-e3cc5abf1330?q=80&w=1000&auto=format&fit=crop"
			};
			int i = 0;
			for (Theater theater : theaters) {
				if (theater.getImageLocation() == null || theater.getImageLocation().contains("600x400") || theater.getImageLocation().isEmpty()) {
					theater.setImageLocation(theaterImages[i % theaterImages.length]);
					i++;
				}
				if (theater.getLocationLink() == null || theater.getLocationLink().contains("location") || theater.getLocationLink().isEmpty()) {
					String query = theater.getName() + " " + (theater.getAddress() != null ? theater.getAddress() : "");
					theater.setLocationLink("https://www.google.com/maps/search/?api=1&query=" + query.replace(" ", "+"));
				}
				theaterRepository.save(theater);
			}
			
			System.out.println(">>> Movie and Theater Data Initialized!");
		};
	}
}