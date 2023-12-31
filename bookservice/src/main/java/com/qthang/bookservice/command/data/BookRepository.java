package com.qthang.bookservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, String> {
    Book getById(String bookId);
}
