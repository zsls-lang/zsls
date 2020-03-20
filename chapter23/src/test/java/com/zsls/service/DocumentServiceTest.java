package com.zsls.service;

import com.zsls.service.base.DocumentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Test
    void addDocument() {
        documentService.addDocument();
    }


    @Test
    void getDocument() {
        documentService.getDocument();
    }

    @Test
    void updateDocument() {
        documentService.updateDocument();
    }

    @Test
    void deleteDocument() {
        documentService.deleteDocument();
    }
}