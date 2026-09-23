package com.example.book_api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "Java Programming", "John Smith", 5),
            new Book(2L, "Web Development", "Sara Ahmad", 3),
            new Book(3L, "Database Systems", "Ali Khan", 4)
    ));

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookInput input
    ) {
        Optional<Book> existingBook = books.stream()
                .filter(book -> book.id().equals(bookId))
                .findFirst();

        if (existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book oldBook = existingBook.get();
        Book updatedBook = new Book(
                oldBook.id(),
                input.title(),
                input.author(),
                input.availableCopies()
        );
        books.set(books.indexOf(oldBook), updatedBook);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        boolean deleted = books.removeIf(book -> book.id().equals(bookId));

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
