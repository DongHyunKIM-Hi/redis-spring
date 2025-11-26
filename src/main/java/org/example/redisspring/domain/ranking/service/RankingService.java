package org.example.redisspring.domain.ranking.service;

import static org.example.redisspring.domain.category.service.CategoryService.CATEGORY_KEY;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.redisspring.domain.ranking.model.RankingDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final StringRedisTemplate stringRedisTemplate;

    public List<RankingDto> findCategoryTopN(int limit) {

        Set<TypedTuple<String>> result = stringRedisTemplate.opsForZSet()
            .reverseRangeWithScores(CATEGORY_KEY, 0, limit - 1);

        if (result == null) {
            return Collections.emptyList();
        }

        return result.stream()
            .map(tuple -> new RankingDto(tuple.getValue(), tuple.getScore()))
            .toList();
    }
}
