package com.zsls.service.aggregation;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AggrMetricServiceTest {

    @Autowired
    private AggrMetricService aggrMetricService;

    @Test
    void aggregationStats() {
        aggrMetricService.aggregationStats();
    }

    @Test
    void aggregationMin() {
        aggrMetricService.aggregationMin();
    }

    @Test
    void aggregationMax() {
    }

    @Test
    void aggregationAvg() {
    }

    @Test
    void aggregationSum() {
    }

    @Test
    void aggregationCount() {
        aggrMetricService.aggregationCount();
    }

    @Test
    void aggregationPercentiles() {
        aggrMetricService.aggregationPercentiles();
    }
}