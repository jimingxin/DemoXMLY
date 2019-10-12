package com.demo.demoxmly.base;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @param <T> the type parameter
 * @Author jimingxin  邮箱： 创建时间: 2019-10-11  18:22 用途: <p>
 ********************************/
public interface IBasePresenter<T> {

    /**
     * Register view callback.
     *
     * @param t the t
     */
    void registerViewCallback(T t);


    /**
     * Un register view callback.
     *
     * @param t the t
     */
    void unRegisterViewCallback(T t);
}