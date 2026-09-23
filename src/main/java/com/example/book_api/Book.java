package com.example.book_api;


public record Book(
        Long id,
        String title,
        String author,
        int availableCopies
) {
}
