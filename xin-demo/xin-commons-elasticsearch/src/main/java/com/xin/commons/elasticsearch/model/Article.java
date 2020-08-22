package com.xin.commons.elasticsearch.model;

import java.io.Serializable;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 文章对象
 */
@Data
@Document(indexName = "blog", type = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 8210249797764830332L;

    private long id;

    private String title;

    private String summary;

    private String content;

    private int pv;

}