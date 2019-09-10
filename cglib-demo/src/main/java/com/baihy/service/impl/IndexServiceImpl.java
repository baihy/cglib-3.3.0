package com.baihy.service.impl;

import com.baihy.service.IndexService;

/**
 * @projectName: cglib-parent
 * @packageName: com.baihy.service.impl
 * @description:
 * @author: huayang.bai
 * @date: 2019/09/10 11:17
 */
public class IndexServiceImpl implements IndexService {

    @Override
    public String index(String param) {
        System.out.println("****************IndexServiceImpl.index****************");
        return param + " hello world";
    }
}
