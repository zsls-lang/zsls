package com.zsls.service.query;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MatchQueryServiceTest {

    @Autowired
    private MatchQueryService matchQueryService;

    @Test
    void matchAllQuery() {
        matchQueryService.matchAllQuery();
    }

    @Test
    void matchQuery() {
        matchQueryService.matchQuery();
    }

    @Test
    void matchPhraseQuery() {
        matchQueryService.matchPhraseQuery();
    }

    @Test
    void matchMultiQuery() {
        matchQueryService.matchMultiQuery();
    }
}