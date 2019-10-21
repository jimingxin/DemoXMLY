package com.demo.demoxmly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demoxmly.adapters.TrackListAdapter;
import com.demo.demoxmly.interfaces.IAlbumDetailViewCallback;
import com.demo.demoxmly.presenters.AlbumDetailPresenter;
import com.demo.demoxmly.utils.ImageBlur;
import com.demo.demoxmly.utils.LogUtil;
import com.demo.demoxmly.views.RoundRectImage;
import com.demo.demoxmly.views.UILoader;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements IAlbumDetailViewCallback {

    private static final String TAG = "DetailActivity";
    private Album album;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        initView();
        initPresenter();
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

        mSubBtn = this.findViewById(R.id.detail_sub_btn);

        //
    }

    private View createSuccessView(ViewGroup container) {

        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list,container,false)
        mDetailList = detailListView.findViewById(R.id.album_detail_list);
        mRefreshLayout = detailListView.findViewById(R.id.refresh_layout);
    }


    private void initPresenter(){
        mAlbumDetailPresenter =AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);

        // RecyclerView使用步骤
        // 第一步：设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDetailList.setLayoutManager(layoutManager);
        // 第二步：设置适配器
        mDetailListAdapter = new TrackListAdapter();
    }



    @Override
    public void onDeatilListLoaded(List<Track> tracks) {

    }

    @Override
    public void onNetworkError(int erroCode, String errorMsg) {

    }

    @Override
    public void onAlbumLoaded(Album album) {
        this.album =album;
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

    }

    @Override
    public void onRefreshFinished(int size) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAlbumDetailPresenter.unRegisterViewCallback(this);
    }
}
