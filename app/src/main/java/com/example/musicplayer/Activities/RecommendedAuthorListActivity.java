package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.Activities.Data.ArtistFile;
import com.example.musicplayer.Activities.Data.MusicFile;
import com.example.musicplayer.R;

public class RecommendedAuthorListActivity extends AppCompatActivity {
    private MyRecyclerViewAdapter<ArtistFile> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.list);

        TextView view = findViewById(R.id.title);
        view.setText("Исполнители");

        recyclerView.setLayoutManager(new LinearLayoutManager(
                RecommendedAuthorListActivity.this,
                LinearLayoutManager.VERTICAL,
                false));

        adapter = new MyRecyclerViewAdapter<ArtistFile>(this, ContentController.getRecommendedAuthors().getValue(), R.drawable.ic_authors, R.layout.recyclerview_item_vertical);

        ContentController.getRecommendedAuthors().observe(this, adapter::setData);

        adapter.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(RecommendedAuthorListActivity.this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        recyclerView.setAdapter(adapter);
    }
}