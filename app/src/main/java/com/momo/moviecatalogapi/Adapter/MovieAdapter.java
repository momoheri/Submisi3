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
import com.momo.moviecatalogapi.model.DataMovie;
import com.momo.moviecatalogapi.service.Path;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<DataMovie> mData;


    public MovieAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void addAll(List<DataMovie> dataMovies) {
        for (DataMovie dataMovie : dataMovies) {
            setData(dataMovie);
        }
    }


    public void setData(DataMovie items) {
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
        final DataMovie dataMovie = mData.get(i);
        viewHolder.bind(dataMovie);
    }

    public DataMovie getItem(int i){
        return mData.get(i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView tvTitle, tvReleaseDate, tvAverage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.img_list);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_year);
            tvAverage = itemView.findViewById(R.id.tv_average);

        }

        void bind(DataMovie movieItem) {
            Glide.with(context)
                    .load(Path.IMG_URL_LIST + movieItem.getPosterPath())
                    .into(photo);
            tvTitle.setText(movieItem.getTitle());
            tvReleaseDate.setText(movieItem.getReleaseDate());
            tvAverage.setText(Double.toString(movieItem.getVoteAverage()));
        }
    }


}
