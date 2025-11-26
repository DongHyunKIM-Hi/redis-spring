package org.example.redisspring.domain.category.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public static final String CATEGORY_KEY = "category";
    public static final String CATEGORY_DAILY_KEY = "category:";
    private final StringRedisTemplate stringRedisTemplate;

    public String findCategoryInfo(String category) {

        stringRedisTemplate.opsForZSet().incrementScore(CATEGORY_KEY, category, 1);

        return category + "을/를 조회 하였습니다.";
    }

    public String findCategoryInfo(String category, LocalDate currentDate) {

        String key = CATEGORY_DAILY_KEY + currentDate.toString();
        stringRedisTemplate.opsForZSet().incrementScore(key, category, 1);

        return category + "을/를 조회 하였습니다.";
    }

}
