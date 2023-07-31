package com.qthang.bookservice.command.aggregate;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.qthang.bookservice.command.command.CreateBookCommand;
import com.qthang.bookservice.command.command.DeleteBookCommand;
import com.qthang.bookservice.command.command.UpdateBookCommand;
import com.qthang.bookservice.command.events.BookCreateEvent;
import com.qthang.bookservice.command.events.BookDeleteEvent;
import com.qthang.bookservice.command.events.BookUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate() {}

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        BookCreateEvent bookCreateEvent = new BookCreateEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreateEvent);
        AggregateLifecycle.apply(bookCreateEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand) {
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdateEvent);
        AggregateLifecycle.apply(bookUpdateEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand) {
        BookDeleteEvent bookDeleteEvent = new BookDeleteEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeleteEvent);
        AggregateLifecycle.apply(bookDeleteEvent);
    }

    @EventSourcingHandler
    public void on(BookCreateEvent event) {
        this.bookId = event.getBookId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event) {
        this.bookId = event.getBookId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getReady();
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event) {
        this.bookId = event.getBookId();
    }
}
