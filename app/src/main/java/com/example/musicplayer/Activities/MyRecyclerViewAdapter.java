package com.example.musicplayer.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Activities.Data.ItemDrawable;
import com.example.musicplayer.R;
import com.squareup.picasso.Picasso;


import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MyRecyclerViewAdapter<T extends ItemDrawable>
        extends RecyclerView.Adapter<MyRecyclerViewAdapter<T>.ViewHolder> {

    private List<T> musicFiles;
    private int idPlaceholder;
    private int idItem;

    private LayoutInflater mInflater;

    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<T> musicFiles, int idPlaceholder, int idItem) {
        this.mInflater = LayoutInflater.from(context);
        this.musicFiles = musicFiles;
        this.idPlaceholder = idPlaceholder;
        this.idItem = idItem;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(idItem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T music = musicFiles.get(position);

        Picasso.get()
                .load(music.getPicturePath())
                .placeholder(idPlaceholder)
                .error(idPlaceholder)
                .centerCrop()
                .resize(120, 120) // изменяем размер картинки до указанной ширины и высоты
                .transform(new RoundedCornersTransformation(10, 0)) // указываем градус, на который следует повернуть картинку
                .into(holder.imageView);

        holder.cardName.setText(music.getTitle());
        holder.cardDefinition.setText(music.getSubTitle());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView cardName;
        TextView cardDefinition;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.colorView);
            cardName = itemView.findViewById(R.id.item_name);
            cardDefinition = itemView.findViewById(R.id.item_definition);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return musicFiles.get(id).getTitle();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setData(List<T> musicFiles)
    {
        if (musicFiles != null)
        {
            this.musicFiles = musicFiles;
            notifyDataSetChanged();
        }
    }
}
