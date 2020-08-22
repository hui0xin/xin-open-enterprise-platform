package com.xin.commons.support.page;

import lombok.Data;

@Data
public class PageVO<T> {
    private T data;
    private PageBody pageParams;
}
