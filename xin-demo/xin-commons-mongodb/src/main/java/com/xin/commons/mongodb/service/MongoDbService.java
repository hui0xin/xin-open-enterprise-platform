package com.xin.commons.mongodb.service;

import com.xin.commons.mongodb.model.Book;
import com.xin.commons.support.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * mongodb 操作
 */
@Slf4j
@Service
public class MongoDbService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存对象
     *
     * @param book
     * @return
     */
    public ResponseResult saveObj(Book book) {
        log.info("--------------------->[MongoDB save start]");
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        mongoTemplate.save(book);
        return ResponseResult.success();
    }


    /**
     * 查询所有
     *
     * @return
     */
    public List<Book> findAll() {
        log.info("--------------------->[MongoDB find start]");
        return mongoTemplate.findAll(Book.class);
    }


    /***
     * 根据id查询
     * @param id
     * @return
     */
    public Book getBookById(String id) {
        log.info("--------------------->[MongoDB find start]");
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public Book getBookByName(String name) {
        log.info("--------------------->[MongoDB find start]");
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Book.class);
    }

    /**
     * 更新对象
     *
     * @param book
     * @return
     */
    public ResponseResult updateBook(Book book) {
        log.info("--------------------->[MongoDB update start]");
        Query query = new Query(Criteria.where("_id").is(book.getId()));
        Update update = new Update().set("publish", book.getPublish())
                .set("info", book.getInfo())
                .set("updateTime", new Date());
        //updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Book.class);
        //updateMulti 更新查询返回结果集的全部
        //mongoTemplate.updateMulti(query,update,Book.class);
        //upsert 更新对象不存在则去添加
        //mongoTemplate.upsert(query,update,Book.class);
        return ResponseResult.success();
    }

    /***
     * 删除对象
     * @param book
     * @return
     */
    public ResponseResult deleteBook(Book book) {
        log.info("--------------------->[MongoDB delete start]");
        mongoTemplate.remove(book);
        return ResponseResult.success();
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public ResponseResult deleteBookById(String id) {
        log.info("--------------------->[MongoDB delete start]");
        //findOne
        Book book = getBookById(id);
        //delete
        deleteBook(book);
        return ResponseResult.success();
    }
}