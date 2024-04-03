package com.example.BookCategory.web.NewFilter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NewsFilter {
    private String title;
    private String author;
    private String categoryName;
}
