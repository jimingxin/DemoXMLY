package com.demo.demoxmly.interfaces;

import com.demo.demoxmly.base.IBasePresenter;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin  邮箱： 创建时间: 2019-10-11  18:27 用途: <p>
 ********************************/
public interface IRecommendPresenter extends IBasePresenter<IRecommendViewCallback> {


    /**
     * Gets recommend list.
     */
    void getRecommendList();
}
