package com.demo.demoxmly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.demoxmly.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * ********************************
 * 项目名称:DemoXMLY
 *
 * @Author jimingxin
 * 邮箱：
 * 创建时间: 2019-10-21  18:47
 * 用途:
 * <p>
 * ********************************
 */
public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.InnerHolder> {

    private static final String TAG = "TrackListAdapter";
    private List<Track> mDetailData = new ArrayList<>();

    // 格式化时间
    private SimpleDateFormat mUpdateDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mDurationFormat = new SimpleDateFormat("mm:ss");
    private ItemLongClickListener mItemLongClickListener;
    private ItemClickListener mItemClickListener;


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        View itemView = holder.itemView;
        // 顺序id
        TextView orderTv = itemView.findViewById(R.id.order_text);
        //标题
        TextView titleTv = itemView.findViewById(R.id.detail_item_title);
        //播放次数
        TextView playCountTv = itemView.findViewById(R.id.detail_item_play_count);
        //时长
        TextView durationTv = itemView.findViewById(R.id.detail_item_duration);
        //更新日期
        TextView updateDateTv = itemView.findViewById(R.id.detail_item_update_time);

        // 设置数据
        final  Track track = mDetailData.get(position);
        orderTv.setText((position + 1) + "");
        titleTv.setText(track.getTrackTitle());
        playCountTv.setText(track.getPlayCount()+"");

        int durationMil = track.getDuration() * 1000;
        String duratin = mDurationFormat.format(durationMil);
        durationTv.setText(duratin);

        String updateTimeText = mUpdateDateFormat.format(track.getUpdatedAt());
        updateDateTv.setText(updateTimeText);

        // 设置Item的点击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null){
                    mItemClickListener.onItemClick(mDetailData,position);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickListener != null){
                    mItemLongClickListener.onItemLongClick(track);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDetailData.size();
    }

    public void setData(List<Track> tracks){
        mDetailData.clear();

        // 添加新的数据
        mDetailData.addAll(tracks);

        // 更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public void setItemClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setItemLongClickListener(ItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(List<Track> detailData, int positon);
    }

    public interface ItemLongClickListener{
        void onItemLongClick(Track track);
    }

}
