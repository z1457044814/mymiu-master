package com.mymiu.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mymiu.R;
import com.mymiu.model.MusicEntity;


import java.util.List;

/**
 * Created by Yang on 2016/9/4.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    List<MusicEntity> data;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener;
    public ContactAdapter(List<MusicEntity> data) {
        this.data = data;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView tag;
        TextView singer_special;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            //歌曲名
            name = (TextView) itemView.findViewById(R.id.music_name_text);
            //索引标记
            tag = (TextView) itemView.findViewById(R.id.letter_head);
            //歌曲和专辑
            singer_special =(TextView)itemView.findViewById(R.id.music_singer_special);
            this.itemView=itemView;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.music_list_item, null);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.onItemLongClick(v,(int)v.getTag(),getItemCount());
                    return true;
                }else {
                    return false;
                }


            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,(int)v.getTag(),getItemCount());
                }

            }
        });
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicEntity musicEntity = data.get(position);
        holder.itemView.setTag(position);
        // 获取首字母的assii值
        int selection = musicEntity.getFirstPinYin().charAt(0);
        // 通过首字母的assii值来判断是否显示字母
        int positionForSelection = getPositionForSelection(selection);
        if (position == positionForSelection) {// 相等说明需要显示字母
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(musicEntity.getFirstPinYin());
        } else {
            holder.tag.setVisibility(View.GONE);
        }
        holder.name.setText(data.get(position).getName());
        holder.singer_special.setText(musicEntity.getSinger()+"·"+musicEntity.getSpecial());
    }
    public int getPositionForSelection(int selection) {
        for (int i = 0; i < data.size(); i++) {
            String Fpinyin = data.get(i).getFirstPinYin();
            char first = Fpinyin.toUpperCase().charAt(0);
            if (first == selection) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position,int itemCount);
    }
    public static interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view , int position,int itemCount);
    }
    //给外部提供方法
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}
