package com.example.BookCategory.Configuration;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {
    private final List<String> cacheNames = new ArrayList<>();
    private final Map<String, CacheProperties> caches = new HashMap<>();
    @Data
    @Getter
    @Setter
    public static class CacheProperties{
        private Duration expiry = Duration.ZERO;
    }
    public interface CacheNames{
        String DATABASE_TITLE_AUTHOR = "titleAuthor";
        String DATABASE_CATEGORY = "categories";
    }
    public enum CacheType{
        IN_MEMORY,REDIS
    }
}
