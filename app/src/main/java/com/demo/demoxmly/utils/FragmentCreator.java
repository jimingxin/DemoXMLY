package com.demo.demoxmly.utils;

import com.demo.demoxmly.base.BaseFragment;
import com.demo.demoxmly.fragments.HistoryFragment;
import com.demo.demoxmly.fragments.RecommendFragment;
import com.demo.demoxmly.fragments.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin  邮箱： 创建时间: 2019-10-12  12:47 用途: <p>
 ********************************/
public class FragmentCreator {
    public final static int INDEX_RECOMMEND = 0;
    public final static int INDEX_SUBSCRIPTION = 1;
    public final static int INDEX_HISTORY = 2;

    public final static int PAGE_COUNT = 3;

    private static Map<Integer, BaseFragment> sCache = new HashMap<>();


    /**
     * 获取
     *
     * @param index the index
     * @return the base fragment
     */
    public static BaseFragment getFragment(int index){
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null){
            return baseFragment;
        }

        switch (index){
            case INDEX_RECOMMEND:
                baseFragment = new RecommendFragment();
                break;
            case INDEX_SUBSCRIPTION:
                baseFragment = new SubscriptionFragment();
                break;
            case INDEX_HISTORY:
                baseFragment = new HistoryFragment();
                break;
        }
        sCache.put(index,baseFragment);
        return baseFragment;
    }
}
