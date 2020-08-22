package com.xin.commons.elasticsearch.repository;

import com.xin.commons.elasticsearch.model.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * 实现了基本的增删改查
 */
@Component
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

}