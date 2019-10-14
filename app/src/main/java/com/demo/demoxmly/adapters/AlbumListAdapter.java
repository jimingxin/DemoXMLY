package com.demo.demoxmly.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.demoxmly.R;
import com.demo.demoxmly.utils.LogUtil;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin
 * 邮箱：
 * 创建时间: 2019-10-12  13:24
 * 用途:
 * <p>
 * ********************************
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.InnerHolder> {

    private static final String TAG = "AlbumListAdapter";
    private List<Album> mData = new ArrayList<>();
    private OnAlbumItemClickListener onAlbumItemClickListener;
    private OnAlbumItemLongClickListener albumItemLongClickListener;


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAlbumItemClickListener != null){
                    int clickPosition = (int)v.getTag();
                    onAlbumItemClickListener.onItemClick(clickPosition,mData.get(clickPosition));
                }
                LogUtil.d(TAG,"holder.itemView click-->"+ (int)v.getTag());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (albumItemLongClickListener!=null){
                    int clickPosition = (int) v.getTag();
                    albumItemLongClickListener.onItemLongClick(mData.get(clickPosition));;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList){
        if (mData != null){
            mData.clear();
            mData.addAll(albumList);
        }
        // 更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album){
            // 找到控件设置
            ImageView albumCoverIv = itemView.findViewById(R.id.album_cover);
            TextView albumt_title = itemView.findViewById(R.id.album_title_tv);
            TextView album_desc = itemView.findViewById(R.id.album_description_tv);

            // 播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            TextView albumContentCountTv = itemView.findViewById(R.id.album_content_size);

            albumt_title.setText(album.getAlbumTitle());
            album_desc.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount()+"");
            albumContentCountTv.setText(album.getIncludeTrackCount() + "");

            // 设置封面图片
            String coverUrlarge = album.getCoverUrlLarge();
            if (!TextUtils.isEmpty(coverUrlarge)){
                Picasso.with(itemView.getContext()).load(coverUrlarge).into(albumCoverIv);
            }else {
                albumCoverIv.setImageResource(R.mipmap.ximalay_logo);
            }
        }
    }

    public  void setAlbumItemClickListener(OnAlbumItemClickListener listener){
        this.onAlbumItemClickListener = listener;
    }

    public interface OnAlbumItemClickListener{
        void onItemClick(int position, Album album);
    }

    public void setOnAlbumItemLongClickListener(OnAlbumItemLongClickListener listener){
        this.albumItemLongClickListener = listener;
    }

    public interface OnAlbumItemLongClickListener{
        void onItemLongClick(Album album);
    }
}
