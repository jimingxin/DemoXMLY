package com.demo.demoxmly;

import android.os.Bundle;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.demoxmly.adapters.IndicatorAdapter;
import com.demo.demoxmly.adapters.MainContentAdapter;
import com.demo.demoxmly.interfaces.IRecommendViewCallback;
import com.demo.demoxmly.utils.FragmentCreator;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;


public class MainActivity extends FragmentActivity implements IRecommendViewCallback {

    private static final String TAG = "MainActivity";
    private MagicIndicator mMagicIndicator;
    private IndicatorAdapter mIndicatorAdapter;
    private ViewPager mContentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();
    }

    private void initEvent() {

        mIndicatorAdapter.setmOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                if (mContentPager != null){
                    mContentPager.setCurrentItem(index,false);
                }
            }
        });

    }


    /**
    * 初始化View
    *
    * @author jimingixn
    * created at 2019-10-12 11:06
    */
    private void initView() {
        mMagicIndicator = this.findViewById(R.id.main_indicator);
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.main_color));

        // 创建indicator的适配器
        mIndicatorAdapter = new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);

        // ViewPager
        mContentPager = this.findViewById(R.id.content_pager);

        // 创建indicator的适配器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(supportFragmentManager);

        mContentPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),0) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return FragmentCreator.getFragment(position);
            }

            @Override
            public int getCount() {
                return FragmentCreator.PAGE_COUNT;
            }
        });
        // 把ViewPage和Indicator绑定
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator,mContentPager);
    }


    @Override
    public void onRecomendListLoaded(List<Album> result) {

    }
}
