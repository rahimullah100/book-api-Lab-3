package com.example.book_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class BookControllerIntegrationTests {

    @Test
    void getAllBooksReturnsInitialBooks() {
        BookController controller = new BookController();

        assertEquals(3, controller.getAllBooks().size());
        assertEquals("Web Development", controller.getAllBooks().get(1).title());
    }

    @Test
    void updateBookReplacesEditableFieldsAndKeepsId() {
        BookController controller = new BookController();

        ResponseEntity<Book> response = controller.updateBook(
                2L,
                new BookInput("Modern Web Development", "Sara Ahmad", 6)
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals(new Book(2L, "Modern Web Development", "Sara Ahmad", 6), response.getBody());
        assertEquals("Modern Web Development", controller.getAllBooks().get(1).title());
    }

    @Test
    void updateMissingBookReturnsNotFound() {
        BookController controller = new BookController();

        ResponseEntity<Book> response = controller.updateBook(
                99L,
                new BookInput("Unknown", "Unknown", 0)
        );

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void deleteBookRemovesItAndReturnsNoContent() {
        BookController controller = new BookController();

        ResponseEntity<Void> response = controller.deleteBook(3L);

        assertEquals(204, response.getStatusCode().value());
        assertEquals(2, controller.getAllBooks().size());
        assertFalse(controller.getAllBooks().stream().anyMatch(book -> book.id().equals(3L)));
    }

    @Test
    void deleteMissingBookReturnsNotFound() {
        BookController controller = new BookController();

        ResponseEntity<Void> response = controller.deleteBook(99L);

        assertEquals(404, response.getStatusCode().value());
    }
}
