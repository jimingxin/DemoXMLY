package com.demo.demoxmly.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin  邮箱： 创建时间: 2019-10-11  18:31 用途: <p>
 ********************************/
public interface IRecommendViewCallback {


    /**
     * On recomend list loaded.
     *
     * @param result the result
     */
    void onRecomendListLoaded(List<Album> result);
}
