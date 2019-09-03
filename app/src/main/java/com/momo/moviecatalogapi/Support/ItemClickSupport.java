package com.momo.moviecatalogapi.Support;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.momo.moviecatalogapi.R;

public class ItemClickSupport {
    private final RecyclerView rv;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = rv.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(rv, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null){
                RecyclerView.ViewHolder holder = rv.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(rv, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (mOnItemClickListener != null){
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null){
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };
    private ItemClickSupport(RecyclerView rv){
        this.rv = rv;
        this.rv.setTag(R.id.item_click_support, this);
        this.rv.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static ItemClickSupport addTo(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null){
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView rv, int i, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView rv, int i, View v);
    }
}
