package com.demo.demoxmly.data;

import com.demo.demoxmly.utils.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Ximalay api.
 */
public class XimalayApi {

    private static XimalayApi sXimalayApi;

    private XimalayApi(){

    }


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

}
