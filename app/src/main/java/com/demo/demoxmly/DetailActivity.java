package com.demo.demoxmly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demoxmly.adapters.TrackListAdapter;
import com.demo.demoxmly.base.BaseActivity;
import com.demo.demoxmly.base.BaseApplication;
import com.demo.demoxmly.interfaces.IAlbumDetailViewCallback;
import com.demo.demoxmly.presenters.AlbumDetailPresenter;
import com.demo.demoxmly.utils.ImageBlur;
import com.demo.demoxmly.utils.LogUtil;
import com.demo.demoxmly.views.RoundRectImage;
import com.demo.demoxmly.views.UILoader;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback, TrackListAdapter.ItemClickListener, UILoader.OnRetryClickListener {

    private static final String TAG = "DetailActivity";
    private Album mCurrentAlbum;
    private AlbumDetailPresenter mAlbumDetailPresenter;

    private ImageView mLargeCover;
    private RoundRectImage mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private TextView mSubBtn;
    private long mCurrentId;
    private int mCurrentPage;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;
    private RecyclerView mDetailList;

    // 播放相关的
    private ImageView mPlayControlBtn;
    private TextView mPlayControlTips;
    private TwinklingRefreshLayout mRefreshLayout;
    private TrackListAdapter mDetailListAdapter;
    private boolean mIsLoaderMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        mCurrentPage = 1;
        initView();
        initPresenter();

        // 添加监听
        initListener();
    }

    private void initListener() {

        mPlayControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG,"订阅按钮别点击");
            }
        });
    }

    private void initView() {

        mDetailListContainer = this.findViewById(R.id.detail_list_container);
        if (mUiLoader == null){
            mUiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {

                    return createSuccessView(container);
                }
            };
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);

        }

        mLargeCover = this.findViewById(R.id.iv_large_cover);
        mSmallCover = this.findViewById(R.id.viv_small_cover);
        mAlbumTitle  = this.findViewById(R.id.tv_album_title);
        mAlbumAuthor = this.findViewById(R.id.tv_album_author);

        // 播放控制的图标
        mPlayControlBtn = this.findViewById(R.id.detail_play_control);
        mPlayControlTips = this.findViewById(R.id.play_control_tv);
        mPlayControlTips.setSelected(true);

        // 订阅按钮
        mSubBtn = this.findViewById(R.id.detail_sub_btn);

    }

    private View createSuccessView(ViewGroup container) {

        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list,container,false);
        mDetailList = detailListView.findViewById(R.id.album_detail_list);
        mRefreshLayout = detailListView.findViewById(R.id.refresh_layout);

        // RecyclerView使用步骤
        // 第一步：设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDetailList.setLayoutManager(layoutManager);
        // 第二步：设置适配器
        mDetailListAdapter = new TrackListAdapter();
        mDetailList.setAdapter(mDetailListAdapter);

        mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5);
                outRect.bottom= UIUtil.dip2px(view.getContext(),5);
                outRect.left= UIUtil.dip2px(view.getContext(),5);
                outRect.right= UIUtil.dip2px(view.getContext(),5);

            }
        });

        mDetailListAdapter.setItemClickListener(this);

        BezierLayout headView = new BezierLayout(this);
        mRefreshLayout.setHeaderView(headView);
        mRefreshLayout.setMaxHeadHeight(140.f);
        mRefreshLayout.setOverScrollBottomShow(false);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                BaseApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this,"刷新成功",Toast.LENGTH_SHORT);
                        mRefreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                // 去加载更多的内容
                if (mAlbumDetailPresenter != null) {
                    mAlbumDetailPresenter.loadMore();
                    mIsLoaderMore = true;
                }
            }
        });

        return  detailListView;
    }


    private void initPresenter(){
        mAlbumDetailPresenter =AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);

    }


    @Override
    public void onDeatilListLoaded(List<Track> tracks) {
        LogUtil.d(TAG,"tracks size:"+tracks.size());

        if (tracks == null || tracks.size() == 0){
            if (mUiLoader != null){
                mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
            }
        }

        if (mUiLoader != null){
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }

        mDetailListAdapter.setData(tracks);
    }

    @Override
    public void onNetworkError(int erroCode, String errorMsg) {
       // 发生网络请求错误显示网络异常状态
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onAlbumLoaded(Album album) {
        this.mCurrentAlbum =album;
        LogUtil.d(TAG,"标题:"+album.getAlbumTitle());

        long id =album.getId();
        mCurrentId = id;

        // 获取专辑的详情内容
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int)mCurrentId, mCurrentPage);
        }

        if (mAlbumTitle != null){
            mAlbumTitle.setText(album.getAlbumTitle());
        }

        if (mAlbumAuthor != null){
            mAlbumAuthor.setText(album.getAnnouncer().getNickname());
        }

        // 做毛玻璃效果
        if (mLargeCover != null && null != mLargeCover){
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    Drawable drawable = mLargeCover.getDrawable();
                    if (drawable != null){
                        ImageBlur.makeBlur(mLargeCover,DetailActivity.this);

                    }
                }

                @Override
                public void onError() {

                }
            });


        }

        if (mSmallCover != null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mSmallCover);
        }


    }

    @Override
    public void onLoaderMoreFinished(int size) {
        if (size > 0){
            Toast.makeText(this,"加载成功",Toast.LENGTH_SHORT);
        }else {
            Toast.makeText(this,"没有更多节目了",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onRefreshFinished(int size) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void onItemClick(List<Track> detailData, int positon) {
        LogUtil.d(TAG,detailData.size() + "");
        Intent intent = new Intent(getApplicationContext(),PlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRetryClick() {
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int) mCurrentAlbum.getId(),mCurrentPage);
        }
    }
}
