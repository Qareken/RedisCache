package com.example.BookCategory.controller;

import com.example.BookCategory.entity.Book;
import com.example.BookCategory.service.impl.BookServiceImpl;
import com.example.BookCategory.web.NewFilter.NewsFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;
    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }
    @GetMapping("/findByAuthorAndTitle")
    public ResponseEntity<Book> findByAuthorAndTitle(@RequestBody(required = false)NewsFilter filter ){
        return ResponseEntity.ok(bookService.findByTitleAndAuthor(filter));
    }
    @GetMapping("/findByCategory")
    public ResponseEntity<List<Book>> findByCategory(@RequestBody NewsFilter filter){
        return ResponseEntity.ok(bookService.findAllByCategory(filter));
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Successfully deleted");
    }
    @PutMapping("/updateBook")
    public  ResponseEntity<Book> updateBook(@RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(book));
    }
    @GetMapping("/findByAuthorAndTitleNative")
    public ResponseEntity<Book> findByAuthorAndTitleNative(@RequestBody(required = false)NewsFilter filter ){
        return ResponseEntity.ok(bookService.findByTitleAndAuthor(filter));
    }
}
