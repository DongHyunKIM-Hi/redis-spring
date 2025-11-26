package org.example.redisspring.domain.ranking.controller;

import java.time.LocalDate;
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

    @GetMapping("/category/last-3-days")
    public ResponseEntity<List<RankingDto>> findTop3CategoryInLast3Days(@RequestParam LocalDate standardDate) {
        return ResponseEntity.ok(rankingService.findTop3CategoryInLast3Days(standardDate));
    }

    @GetMapping("/category")
    public ResponseEntity<List<RankingDto>> findCategoryTop3() {
        return ResponseEntity.ok(rankingService.findCategoryTop3());
    }

}
