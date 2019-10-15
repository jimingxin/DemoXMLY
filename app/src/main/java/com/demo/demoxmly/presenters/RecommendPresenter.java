package com.demo.demoxmly.presenters;

import com.demo.demoxmly.data.XimalayApi;
import com.demo.demoxmly.interfaces.IRecommendPresenter;
import com.demo.demoxmly.interfaces.IRecommendViewCallback;
import com.demo.demoxmly.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.List;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin  邮箱： 创建时间: 2019-10-11  18:32 用途: <p>
 ********************************/
public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";
    private List<Album> mCurrentRecommend = null;
    private List<Album> mRecommendList;
    private List<IRecommendViewCallback> mCallbacks = new ArrayList<>();

    private static RecommendPresenter sInstance = null;

    private RecommendPresenter() {

    }

    /**
     * Get instance recommend presenter.
     *
     * @return the recommend presenter
     */
    public static RecommendPresenter getInstance(){
        if (sInstance == null){
            synchronized (RecommendPresenter.class){
                if (sInstance == null){
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }


    /**
     * 获取当前推荐专辑列表
     *
     * @return the current recommend
     */
    public List<Album> getmCurrentRecommend() {
        return mCurrentRecommend;
    }

    @Override
    public void getRecommendList() {
        if (mRecommendList != null && mRecommendList.size() > 0){
            handlerRecommendResult(mRecommendList);
            return;
        }

        updateLoading();
        XimalayApi ximalayApi = XimalayApi.getXimalayApi();
        ximalayApi.getRecommendList(new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                LogUtil.d(TAG,"thread name -->"+Thread.currentThread().getName());
                LogUtil.d(TAG,"albumList count :" + gussLikeAlbumList.getAlbumList().size());
                mRecommendList = gussLikeAlbumList.getAlbumList();

                handlerRecommendResult(mRecommendList);
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"error:"+s);
            }
        });
    }

    private void updateLoading() {
        for (IRecommendViewCallback callback : mCallbacks){
            callback.onLoading();
        }
    }

    private void handlerRecommendResult(List<Album> mRecommendList) {

        if (mRecommendList != null){
            if (mRecommendList.size() == 0){

            }else {
                for (IRecommendViewCallback callback : mCallbacks){
                    callback.onRecomendListLoaded(mRecommendList);
                }
                this.mCurrentRecommend = mRecommendList;
            }
        }
    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null && !mCallbacks.contains(callback)){
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (mCallbacks != null){
            mCallbacks.remove(callback);
        }
    }
}
