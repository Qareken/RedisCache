package com.example.BookCategory.service.specification;

import com.example.BookCategory.entity.Book;
import com.example.BookCategory.entity.Category;
import com.example.BookCategory.web.NewFilter.NewsFilter;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface BookSpecification {

    static Specification<Book> findByTitleAndAuthor(NewsFilter newsFilter) {

        return Specification.where(byTitle(newsFilter.getTitle())).and(byAuthor(newsFilter.getAuthor()));
    }

    static Specification<Book> byTitle(String title) {
        return ((root, query, criteriaBuilder) -> {
            if(title==null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        });
    }
    static Specification<Book> byAuthor(String author) {

        return ((root, query, criteriaBuilder) -> {
            if(author==null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(root.get("author"), "%" + author + "%");
        });
    }

    static Specification<Book> findByCategory(String categoryName) {
        return (((root, query, criteriaBuilder) -> {
            Join<Book, Category> join = root.join("category", JoinType.INNER);
            return criteriaBuilder.like(join.get("name"), "%" + categoryName + "%");
        }));
    }
}
