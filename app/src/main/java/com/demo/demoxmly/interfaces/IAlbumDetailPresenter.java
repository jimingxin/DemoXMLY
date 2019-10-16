package com.demo.demoxmly.interfaces;

import com.demo.demoxmly.base.IBasePresenter;

/**
 * The interface Album detail presenter.
 *
 * @ProjectName: DemoXMLY
 * @Package: com.demo.demoxmly.interfaces
 * @ClassName: IAlbumDetailPresenter
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2019 -10-16 21:08
 * @UpdateUser: 更新者 ：
 * @UpdateDate: 2019 -10-16 21:08
 * @UpdateRemark: 更新说明 ：
 * @Version: 1.0
 */
public interface IAlbumDetailPresenter extends IBasePresenter {

    /**
     * Pull 2 refresh more.
     *
     * @param
     * @return
     * @method 下拉下载更多内容
     * @description 描述一下方法的作用
     * @date: 2019 -10-16 21:09
     * @author: 作者名
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();


    /**
     * Gets album detail.
     *
     * @param albumId the album id
     * @param page    the page
     */
    void getAlbumDetail(int albumId, int page);
}
