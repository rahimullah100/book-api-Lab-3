package com.example.book_api;


public record BookInput(
        String title,
        String author,
        int availableCopies
) {
}
