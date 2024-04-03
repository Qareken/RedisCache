package com.example.BookCategory.service.impl;

import com.example.BookCategory.Configuration.AppCacheProperties;
import com.example.BookCategory.entity.Book;
import com.example.BookCategory.exception.BookNotFoundException;
import com.example.BookCategory.repository.BookRepository;
import com.example.BookCategory.repository.CategoryRepository;
import com.example.BookCategory.service.BookService;
import com.example.BookCategory.service.specification.BookSpecification;
import com.example.BookCategory.web.NewFilter.NewsFilter;
import lombok.AllArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.DATABASE_TITLE_AUTHOR, key = "#book.title+'-'+#book.author", beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.DATABASE_CATEGORY, key = "#book.category.name", beforeInvocation = true)
    })
    public Book save(Book book) {
        var savedCategory = categoryRepository.save(book.getCategory());
        book.setCategory(savedCategory);
        return bookRepository.save(book);
    }

    @Override
    @SneakyThrows
    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_TITLE_AUTHOR, key = "#filter.title+'-'+#filter.author")
    public Book findByTitleAndAuthor(NewsFilter filter) {
       var listBook = bookRepository.findAll(BookSpecification.findByTitleAndAuthor( filter));
       if(listBook.isEmpty()){
           throw new BookNotFoundException(MessageFormat.format("Book with these parameters {0} and {1} not found",
                   filter.getTitle(), filter.getAuthor()));
       }
        return listBook.get(0);
    }

    @Override
    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_CATEGORY, key = "#filter.categoryName")
    public List<Book> findAllByCategory(NewsFilter filter) {
        return bookRepository.findAll(BookSpecification.findByCategory(filter.getCategoryName()));
    }

    @Override
    @SneakyThrows
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(MessageFormat.format("with these {} not found ", id)));
    }

    @Override
    @SneakyThrows
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.DATABASE_TITLE_AUTHOR, key = "#book.title+'-'+#book.author", beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.DATABASE_CATEGORY, key = "#book.category.name", beforeInvocation = true)
    })
    public Book updateBook(Book book) {
        Optional<Book> existedBookOpt = bookRepository.findBookByTitle(book.getTitle());
        if (existedBookOpt.isPresent()) {
            Book existedBook = existedBookOpt.get();

            // Используем рефлексию для обновления только не-null и не-empty полей
            Field[] fields = Book.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(book);
                if (value != null && !value.toString().isEmpty()) {

                    BeanUtils.copyProperties(book, existedBook, field.getName());
                }
            }

            return bookRepository.save(existedBook);
        } else {
            throw new BookNotFoundException("Book not found");
        }
    }

    @Override

    public void deleteBook(Long id) {
        var book = findById(id);
        deleteFromCache(book);
        bookRepository.deleteById(id);
    }
    @Caching(evict = {
            @CacheEvict(value = AppCacheProperties.CacheNames.DATABASE_TITLE_AUTHOR, key = "#book.title+'-'+#book.author", beforeInvocation = true),
            @CacheEvict(value = AppCacheProperties.CacheNames.DATABASE_CATEGORY, key = "#book.category.name", beforeInvocation = true)
    })
    protected void  deleteFromCache(Book book){
    }

    @Override
    @Cacheable(cacheNames = AppCacheProperties.CacheNames.DATABASE_CATEGORY, key = "#filter.categoryName")
    public Book findByAuthorAndTitleNative(NewsFilter filter) {
        return bookRepository.findBooksByTitleAndAuthor(filter.getTitle(), filter.getAuthor());
    }
}
