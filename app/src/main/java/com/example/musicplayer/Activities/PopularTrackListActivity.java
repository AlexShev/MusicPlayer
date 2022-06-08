package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.Activities.Data.MusicFile;
import com.example.musicplayer.R;

public class PopularTrackListActivity extends AppCompatActivity {
    private MyRecyclerViewAdapter<MusicFile> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.list);

        TextView view = findViewById(R.id.title);
        view.setText("Треки");

        recyclerView.setLayoutManager(new LinearLayoutManager(
                PopularTrackListActivity.this,
                LinearLayoutManager.VERTICAL,
                false));

        adapter = new MyRecyclerViewAdapter<MusicFile>(this, ContentController.getPopularTracks().getValue(), R.drawable.ic_music, R.layout.recyclerview_item_vertical);

        ContentController.getPopularTracks().observe(this, adapter::setData);

        adapter.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(PopularTrackListActivity.this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        recyclerView.setAdapter(adapter);
    }
}