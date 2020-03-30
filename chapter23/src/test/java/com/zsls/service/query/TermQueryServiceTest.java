package com.zsls.service.query;

import com.zsls.service.base.IndexService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TermQueryServiceTest {
    @Autowired
    private TermQueryService termQueryService;
    @Test
    void termQuery() {
        termQueryService.termQuery();
    }

    @Test
    void termsQuery() {
        termQueryService.termsQuery();
    }
}