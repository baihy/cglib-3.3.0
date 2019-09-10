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

    private String name;

    public IndexServiceImpl(String name) {
        this.name = name;
    }

    @Override
    public String index0(String param) {
        System.out.println("****************IndexServiceImpl.index0****************");
        return param;
    }

    @Override
    public String index1(String param) {
        System.out.println("****************IndexServiceImpl.index1****************");
        return param;
    }

    @Override
    public String index2(String param) {
        System.out.println("****************IndexServiceImpl.index2****************");
        return param;
    }
}
