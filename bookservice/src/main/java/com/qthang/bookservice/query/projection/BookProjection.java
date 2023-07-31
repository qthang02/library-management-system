package com.qthang.bookservice.query.projection;

import com.qthang.bookservice.command.data.Book;
import com.qthang.bookservice.command.data.BookRepository;
import com.qthang.bookservice.query.model.BookResponseModel;
import com.qthang.bookservice.query.queries.GetAllBookQuery;
import com.qthang.bookservice.query.queries.GetBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery) {
        BookResponseModel model = new BookResponseModel();
        Book book = bookRepository.getById(getBookQuery.getBookId());
        BeanUtils.copyProperties(book, model);

        return model;
    }

    @QueryHandler
    List<BookResponseModel> handle(GetAllBookQuery getAllBookQuery) {
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseModel> listBook = new ArrayList<>();
        listEntity.forEach(s -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(s, model);
            listBook.add(model);
        });

        return listBook;
    }
}
