package com.bookstore;

import com.bookstore.projection.AuthorNameAge;
import java.util.List;
import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            List<AuthorNameAge> authors = bookstoreService.fetchByAge();

            System.out.println("Number of authors:" + authors.size());

            for (AuthorNameAge author : authors) {
                System.out.println("Author name: " + author.getName()
                        + " | Age: " + author.years() + " | Rank: " + author.rank()
                        + " | Books: " + author.books());
            }
        };
    }
}

/*
 * How To Enrich DTO With Virtual Properties Via Spring Projections

Note: You may also like to read the recipe, "How To Create DTO Via Spring Data Projections"

Description: This is an application sample that fetches only the needed columns from the database via Spring Data Projections (DTO) and enrich the result via virtual properties.

Key points:

we fetch from the database only the author name and age
in the projection interface, AuthorNameAge, use the @Value and Spring SpEL to point to a backing property from the domain model (in this case, the domain model property age is exposed via the virtual property years)
in the projection interface, AuthorNameAge, use the @Value and Spring SpEL to enrich the result with two virtual properties that don't have a match in the domain model (in this case, rank and books)
Output example:
*
*/
