package com.zsls.service.query;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class BoolQueryServiceTest {
    @Autowired
    private BoolQueryService boolQueryService;

    @Test
    void wildcardQuery() {
        boolQueryService.boolQuery();
    }
}