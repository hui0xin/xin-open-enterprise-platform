package com.xin.commons.elasticsearch.controller;

import com.xin.commons.elasticsearch.model.Article;
import com.xin.commons.elasticsearch.repository.ArticleRepository;
import com.xin.commons.support.response.ResponseResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/save")
    public ResponseResult save(long id, String title) {
        Article article = new Article();
        article.setId(id);
        article.setPv(123);
        article.setContent("Springboot整合Elasticsearch");
        article.setTitle(title);
        article.setSummary("搜索框架整合");
        articleRepository.save(article);
        return ResponseResult.success();
    }

    @GetMapping("/search")
    public ResponseResult search(String title) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title);
        Iterable<Article> list = articleRepository.search(queryBuilder);
        return ResponseResult.success(list);
    }

}