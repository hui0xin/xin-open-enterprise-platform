package com.xin.commons.mongodb.controller;

import com.xin.commons.mongodb.model.Book;
import com.xin.commons.mongodb.service.MongoDbService;
import com.xin.commons.support.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BaseController {

    @Autowired
    private MongoDbService mongoDbService;

    @PostMapping("/mongo/save")
    public ResponseResult saveObj(@RequestBody Book book) {
        return mongoDbService.saveObj(book);
    }

    @GetMapping("/mongo/findAll")
    public ResponseResult<List<Book>> findAll() {
        return ResponseResult.success(mongoDbService.findAll());
    }
    @GetMapping("/mongo/findOne")
    public ResponseResult<Book> findOne(@RequestParam String id) {
        return ResponseResult.success(mongoDbService.getBookById(id));
    }

    @GetMapping("/mongo/findOneByName")
    public ResponseResult<Book> findOneByName(@RequestParam String name) {
        return ResponseResult.success(mongoDbService.getBookByName(name));
    }

    @PostMapping("/mongo/update")
    public ResponseResult update(@RequestBody Book book) {
        return mongoDbService.updateBook(book);
    }

    @PostMapping("/mongo/delOne")
    public ResponseResult delOne(@RequestBody Book book) {
        return mongoDbService.deleteBook(book);
    }

    @GetMapping("/mongo/delById")
    public ResponseResult delById(@RequestParam String id) {
        return mongoDbService.deleteBookById(id);
    }
}