package com.zsls.service;

import com.zsls.service.base.IndexService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class IndexServiceTest {

    @Autowired
    private IndexService indexService;

    @org.junit.jupiter.api.Test
    void createIndex() {
        indexService.createIndex();
    }

    @org.junit.jupiter.api.Test
    void deleteIndex() {
        indexService.deleteIndex();
    }
    @org.junit.jupiter.api.Test
    void existsIndex() {
        indexService.existsIndex();
    }
}