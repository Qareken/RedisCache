package com.example.BookCategory.service;

import com.example.BookCategory.entity.Book;
import com.example.BookCategory.web.NewFilter.NewsFilter;

import java.util.List;

public interface BookService {
    Book save (Book book);
    Book findByTitleAndAuthor(NewsFilter filter);

   List<Book> findAllByCategory(NewsFilter filter);
   Book findById(Long id);

   Book updateBook(Book book);
   void deleteBook (Long id);
   Book findByAuthorAndTitleNative(NewsFilter filter);
    
}
