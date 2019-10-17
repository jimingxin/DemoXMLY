package com.demo.demoxmly.data;

import com.demo.demoxmly.utils.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Ximalay api.
 */
public class XimalayApi {

    private static XimalayApi sXimalayApi;

    private XimalayApi(){

    }


    /**
     * Get ximalay api ximalay api.
     *
     * @return the ximalay api
     */
    public static XimalayApi getXimalayApi(){
        if (sXimalayApi == null){
            synchronized (XimalayApi.class){
                if (sXimalayApi == null){
                    sXimalayApi = new  XimalayApi();
                }
            }
        }
        return sXimalayApi;
    }


    /**
     * 获取推荐内容
     *
     * @param callBack 请求结果的回调接口
     */
    public void getRecommendList(IDataCallBack<GussLikeAlbumList> callBack){
        Map<String,String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_RECOMMEND+"");
        CommonRequest.getGuessLikeAlbum(map,callBack);
    }


    /**
     * Get album detail.
     *
     * @param callback  the call back
     * @param albumId   the album id
     * @param pageIndex the page index
     */
    public void getAlbumDetail(IDataCallBack<TrackList> callback,long albumId, int pageIndex){
        Map<String,String> map = new HashMap<>();
        map.put(DTransferConstants.SORT,"asc");
        map.put(DTransferConstants.ALBUM_ID,albumId+"");
        map.put(DTransferConstants.PAGE, pageIndex + "");
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT + "");
        CommonRequest.getTracks(map, callback);

    }

}
