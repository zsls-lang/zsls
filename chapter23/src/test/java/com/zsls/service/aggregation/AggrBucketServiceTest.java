package com.zsls.service.aggregation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AggrBucketServiceTest {
    @Autowired
    private AggrBucketService aggrBucketService;

    @Test
    void aggrBucketTerms() {
        aggrBucketService.aggrBucketTerms();
    }

    @Test
    void aggrBucketRange() {
        aggrBucketService.aggrBucketRange();
    }

    @Test
    void aggrBucketDateRange() {
        aggrBucketService.aggrBucketDateRange();
    }

    @Test
    void aggrBucketHistogram() {
        aggrBucketService.aggrBucketHistogram();
    }

    @Test
    void aggrBucketDateHistogram() {
        aggrBucketService.aggrBucketDateHistogram();
    }
}