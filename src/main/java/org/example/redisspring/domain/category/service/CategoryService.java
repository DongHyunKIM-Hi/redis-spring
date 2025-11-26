package org.example.redisspring.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public static final String CATEGORY_KEY = "category";
    private final StringRedisTemplate stringRedisTemplate;

    public String findCategoryInfo(String category) {

        stringRedisTemplate.opsForZSet().incrementScore(CATEGORY_KEY, category, 1);

        return category + "을/를 조회 하였습니다.";
    }

}
