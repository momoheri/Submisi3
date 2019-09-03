package com.momo.moviecatalogapi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.momo.moviecatalogapi.R;
import com.momo.moviecatalogapi.model.DataTvShow;
import com.momo.moviecatalogapi.service.Path;

import java.util.ArrayList;
import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private Context context;
    private List<DataTvShow> mData;

    public TvShowAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void addAll(List<DataTvShow> dataTvShows) {
        for (DataTvShow dataTvShow : dataTvShows) {
            setData(dataTvShow);
        }
    }

    public void setData(DataTvShow items) {
        mData.add(items);
        notifyItemInserted(mData.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DataTvShow dataTvShow = mData.get(i);
        viewHolder.bind(dataTvShow);
    }

    public DataTvShow getItem(int i) {
        return mData.get(i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView tvTitle, tvYear, tvAverage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.img_list);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvAverage = itemView.findViewById(R.id.tv_average);
        }

        void bind(DataTvShow items) {
            Glide.with(context)
                    .load(Path.IMG_URL_LIST + items.getPoster_path())
                    .into(poster);
            tvTitle.setText(items.getTitle());
            tvYear.setText(items.getFirst_air_date());
            tvAverage.setText(Double.toString(items.getVote_average()));
        }
    }
}
