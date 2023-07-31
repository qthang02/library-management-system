package com.qthang.bookservice.command.events;

import com.qthang.bookservice.command.data.Book;
import com.qthang.bookservice.command.data.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreateEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdateEvent event) {
        Book book = bookRepository.getById(event.getBookId());

        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setReady(event.getReady());

        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookDeleteEvent event) {
        bookRepository.deleteById(event.getBookId());
    }
}
