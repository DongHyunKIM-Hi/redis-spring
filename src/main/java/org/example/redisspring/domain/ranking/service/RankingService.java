package org.example.redisspring.domain.ranking.service;

import static org.example.redisspring.domain.category.service.CategoryService.CATEGORY_DAILY_KEY;
import static org.example.redisspring.domain.category.service.CategoryService.CATEGORY_KEY;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.redisspring.domain.ranking.model.RankingDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final StringRedisTemplate stringRedisTemplate;

    public List<RankingDto> findCategoryTop3() {

        Set<TypedTuple<String>> result = stringRedisTemplate.opsForZSet()
            .reverseRangeWithScores(CATEGORY_KEY, 0, 2);

        if (result == null) {
            return Collections.emptyList();
        }

        return result.stream()
            .map(tuple -> new RankingDto(tuple.getValue(), tuple.getScore()))
            .toList();
    }

    public List<RankingDto> findTop3CategoryInLast3Days(LocalDate standardDate) {

        /*
        1. `날짜 별로 정리된 Key를 기준으로 필요한 ZSET을 선별한다`
        2. `선별한 N개의 ZSET 들을 1개의 ZSET로 만든다`
        3. `만들어진 ZSET 기준으로 순위를 조회 한다.`
        */

        // 최근 3일 간 데이터 가져오기
        List<String> keys = List.of(
            CATEGORY_DAILY_KEY + standardDate.toString(),
            CATEGORY_DAILY_KEY + standardDate.minusDays(1).toString(),
            CATEGORY_DAILY_KEY + standardDate.minusDays(2).toString()
        );

        // 결과를 저장할 임시 key
        String destKey = "category_rank:last3days";

        // ZSET 합치기
        stringRedisTemplate.opsForZSet().unionAndStore(
            keys.get(0),
            keys.subList(1, keys.size()),
            destKey
        );

        // 합친 ZSET 기준으로 탑 3 뽑기
        Set<ZSetOperations.TypedTuple<String>> result = stringRedisTemplate.opsForZSet()
            .reverseRangeWithScores(destKey, 0, 2);

        if (result == null) {
            return Collections.emptyList();
        }

        return result.stream()
            .map(tuple -> new RankingDto(tuple.getValue(), tuple.getScore()))
            .toList();

    }
}
