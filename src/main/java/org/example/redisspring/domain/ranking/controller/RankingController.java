package org.example.redisspring.domain.ranking.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.redisspring.domain.ranking.model.RankingDto;
import org.example.redisspring.domain.ranking.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/category")
    public ResponseEntity<List<RankingDto>> findCategoryTopN(@RequestParam(defaultValue = "3") int limit) {
        return ResponseEntity.ok(rankingService.findCategoryTopN(limit));
    }

}
