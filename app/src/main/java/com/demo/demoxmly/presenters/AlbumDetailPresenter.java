package com.demo.demoxmly.presenters;

import com.demo.demoxmly.adapters.AlbumListAdapter;
import com.demo.demoxmly.data.XimalayApi;
import com.demo.demoxmly.interfaces.IAlbumDetailPresenter;
import com.demo.demoxmly.interfaces.IAlbumDetailViewCallback;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.List;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin
 * 邮箱：
 * 创建时间: 2019-10-17  17:47
 * 用途:
 * <p>
 * ********************************
 */
public class AlbumDetailPresenter implements IAlbumDetailPresenter {

    private static final String TAG = "AlbumDetailPresenter";
    private List<IAlbumDetailViewCallback> mCallbacks = new ArrayList<>();
    private List<Track> mTracks = new ArrayList<>();


    private Album mTargetAlbum = null;
    private int mCurrentPageIndex = 0;
    private int mCurrentAlbumId = -1;

    private AlbumDetailPresenter(){

    }

    private static AlbumDetailPresenter sInstance = null;

    public static AlbumDetailPresenter getInstance(){
        if (sInstance == null){
            synchronized (AlbumDetailPresenter.class){
                sInstance = new  AlbumDetailPresenter();
            }
        }
        return sInstance;
    }




    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {
        mCurrentPageIndex++;
        doLoaded(true);
    }

    private void doLoaded(final boolean isLoaderMore) {
        XimalayApi ximalayApi = XimalayApi.getXimalayApi();
        ximalayApi.getAlbumDetail(new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                if (trackList != null){
                    List<Track> tracks = trackList.getTracks();

                    if (isLoaderMore){
                        //
                        mTracks.addAll(tracks);
                        int size = tracks.size();
                        handlerLoaderMoreResult(size);
                    }else {
                        mTracks.addAll(0,tracks);
                    }
                    handlerAlbumDetailResult(mTracks);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        },mCurrentAlbumId,mCurrentPageIndex);
    }

    private void handlerAlbumDetailResult(List<Track> mTracks) {
        for (IAlbumDetailViewCallback mCallback : mCallbacks) {
            mCallback.onDeatilListLoaded(mTracks);
        }
    }

    private void handlerLoaderMoreResult(int size) {
        for (IAlbumDetailViewCallback callback:mCallbacks){
            callback.onLoaderMoreFinished(size);
        }
    }

    @Override
    public void getAlbumDetail(int albumId, int page) {

    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallback detailViewCallback) {
        if (!mCallbacks.contains(detailViewCallback)){
           mCallbacks.add(detailViewCallback);
           if (mTargetAlbum != null){
               detailViewCallback.onAlbumLoaded(mTargetAlbum);
           }
        }
    }

    @Override
    public void unRegisterViewCallback(IAlbumDetailViewCallback detailViewCallback) {
        mCallbacks.remove(detailViewCallback);
    }
}
