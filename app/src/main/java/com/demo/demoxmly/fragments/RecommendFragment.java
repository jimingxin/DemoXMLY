package com.demo.demoxmly.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.demoxmly.R;
import com.demo.demoxmly.adapters.AlbumListAdapter;
import com.demo.demoxmly.base.BaseFragment;
import com.demo.demoxmly.interfaces.IRecommendViewCallback;
import com.demo.demoxmly.presenters.RecommendPresenter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin
 * 邮箱：
 * 创建时间: 2019-10-11  19:43
 * 用途:
 * <p>
 * ********************************
 */
public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {

    private RecyclerView mREcommendRv;
    private AlbumListAdapter mRecommendListAdapter;
    private RecommendPresenter mRecommendPresenter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        /*
        *inflate里面把ViewGroup传进去了，因为每一个View只能有一个父view即parentView。当container不为空时，比如此fragment所待在的activity的layout。
        * 而onCreateView中返回的view是给ViewPager使用的，所以就会出现这个view有两个parentView－即activity的layout和viewPager，
        * 所以会报出异常。只要如下解决即可：
        * infalte的时候把ViewGroup参数设置为null，view的parentView是ViewPager，ViewPager的parentView是activity的layout。
        */
        View mRootView = layoutInflater.inflate(R.layout.fragment_recomment,null);

        //找到控件
        mREcommendRv = mRootView.findViewById(R.id.recommend_list);
        TwinklingRefreshLayout twinklingRefreshLayout = mRootView.findViewById(R.id.over_scroll_view);
        twinklingRefreshLayout.setPureScrollModeOn();

        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mREcommendRv.setLayoutManager(linearLayoutManager);

        // 设置适配器
        mRecommendListAdapter = new AlbumListAdapter();
        mREcommendRv.setAdapter(mRecommendListAdapter);


        // 获取逻辑层
        mRecommendPresenter = RecommendPresenter.getInstance();
        mRecommendPresenter.registerViewCallback(this);
        mRecommendPresenter.getRecommendList();
        return mRootView;
    }

    @Override
    public void onRecomendListLoaded(List<Album> result) {
        mRecommendListAdapter.setData(result);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecommendPresenter != null){
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }
}
