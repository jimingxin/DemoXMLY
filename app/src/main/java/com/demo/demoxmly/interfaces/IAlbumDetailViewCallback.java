package com.demo.demoxmly.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/**     
  * 
  * @ProjectName:    专辑点击接口回调
  * @Package:        com.demo.demoxmly.interfaces
  * @ClassName:      IAlbumDetailViewCallback
  * @Description:     java类作用描述 
  * @Author:         作者名
  * @CreateDate:     2019-10-16 20:59
  * @UpdateUser:     更新者：
  * @UpdateDate:     2019-10-16 20:59
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public interface IAlbumDetailViewCallback {

     /**
      * @method  专辑详情内容加载出来了
      * @description 描述一下方法的作用
      * @date: 2019-10-16 21:01
      * @author: 作者名
      * @param tracks
      * @return
      */
    void onDeatilListLoaded(List<Track> tracks);

     /**
      * @method  网络错误
      * @description 描述一下方法的作用
      * @date: 2019-10-16 21:02
      * @author: 作者名
      * @param 
      * @return 
      */
    void onNetworkError(int erroCode, String errorMsg);

     /**
      * @method  加载更多结果
      * @description 描述一下方法的作用
      * @date: 2019-10-16 21:05
      * @author: 作者名
      * @param
      * @return
      */
    void onAlbumLoaded(Album album);

     /**
      * @method  加载更多内容
      * @description 描述一下方法的作用
      * @date: 2019-10-16 21:06
      * @author: 作者名
      * @param size size>0表示加载成功，否则表示加载失败.
      * @return
      */
    void onLoaderMoreFinished(int size);

     /**
      * @method  下拉加载更多结果
      * @description 描述一下方法的作用
      * @date: 2019-10-16 21:07
      * @author: 作者名
      * @param size size>0表示加载成功，否则表示加载失败.
      * @return 
      */
    void  onRefreshFinished(int size);
}
