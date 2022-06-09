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

public class RecommendedTrackListActivity extends AppCompatActivity {
    private MyRecyclerViewAdapter<MusicFile> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.list);

        TextView view = findViewById(R.id.title);
        view.setText("Треки");

        recyclerView.setLayoutManager(new LinearLayoutManager(
                RecommendedTrackListActivity.this,
                LinearLayoutManager.VERTICAL,
                false));

        adapter = new MyRecyclerViewAdapter<MusicFile>(this, ContentController.getRecommendedTracks().getValue(), R.drawable.ic_music, R.layout.recyclerview_item_vertical);

        ContentController.getRecommendedTracks().observe(this, adapter::setData);

        adapter.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(RecommendedTrackListActivity.this, "You clicked " + adapter.getItemInfo(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        recyclerView.setAdapter(adapter);
    }
}