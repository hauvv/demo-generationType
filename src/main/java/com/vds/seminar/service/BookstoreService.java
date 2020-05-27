package com.vds.seminar.service;
import java.util.ArrayList;
import java.util.List;

import com.vds.seminar.entity.Author;
import com.vds.seminar.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookstoreService {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final AuthorRepository authorRepository;

    public BookstoreService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void batchAuthors() {
        long start = System.currentTimeMillis();
        List<Author> authors = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Author author = new Author();
            //author.setId((long) i + 1);
            author.setName("Name_" + i);
            author.setGenre("Genre_" + i);
            author.setAge(18 + i);

            authors.add(author);

            if (i % batchSize == 0 && i > 0) {
                authorRepository.saveAll(authors);
                authors.clear();
            }
        }

        if (authors.size() > 0) {
            authorRepository.saveAll(authors);
            authors.clear();
        }
        System.out.println("Take: "+ (System.currentTimeMillis()-start) +" ms");
    }
}
