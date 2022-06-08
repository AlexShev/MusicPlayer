package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.musicplayer.Activities.Data.ArtistFile;
import com.example.musicplayer.Activities.Data.MusicFile;
import com.example.musicplayer.R;

public class MainActivity extends AppCompatActivity {

    private MyRecyclerViewAdapter<MusicFile> adapterRecommendedTrack;
    private MyRecyclerViewAdapter<ArtistFile> adapterRecommendedAuthors;
    private MyRecyclerViewAdapter<MusicFile> adapterPopularTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the RecyclerView
        RecyclerView recyclerViewRecommendedTrack = findViewById(R.id.recommendedTrack);
        RecyclerView recyclerViewRecommendedAuthors = findViewById(R.id.recommendedAuthors);
        RecyclerView recyclerViewPopularTrack = findViewById(R.id.popularTrack);


        recyclerViewRecommendedTrack.setLayoutManager(new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        recyclerViewRecommendedAuthors.setLayoutManager(new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        recyclerViewPopularTrack.setLayoutManager(new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));

        adapterRecommendedTrack = new MyRecyclerViewAdapter<MusicFile>(this, ContentController.getRecommendedTracks().getValue(), R.drawable.ic_music, R.layout.recyclerview_item);
        adapterRecommendedAuthors = new MyRecyclerViewAdapter<ArtistFile>(this, ContentController.getRecommendedAuthors().getValue(), R.drawable.ic_authors, R.layout.recyclerview_item);

        adapterPopularTrack = new MyRecyclerViewAdapter<MusicFile>(this, ContentController.getPopularTracks().getValue(), R.drawable.ic_music, R.layout.recyclerview_item);


        ContentController.getRecommendedTracks().observe(this, adapterRecommendedTrack::setData);
        ContentController.getRecommendedAuthors().observe(this, adapterRecommendedAuthors::setData);
        ContentController.getPopularTracks().observe(this, adapterPopularTrack::setData);

        new MusicLoader(new MutableLiveData<>(), this).start();

        adapterRecommendedTrack.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "You clicked " + adapterRecommendedTrack.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        adapterRecommendedAuthors.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "You clicked " + adapterRecommendedAuthors.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        adapterPopularTrack.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "You clicked " + adapterPopularTrack.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );


        recyclerViewRecommendedTrack.setAdapter(adapterRecommendedTrack);
        recyclerViewRecommendedAuthors.setAdapter(adapterRecommendedAuthors);
        recyclerViewPopularTrack.setAdapter(adapterPopularTrack);


        findViewById(R.id.more_recommendedTrack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                    }
                }
        );
    }
}


//        String responseJSON =
//                "["+
//                "{\n" +
//                "  \"id\": \"100\",\n" +
//                "  \"name\": \"Molecule\",\n" +
//                "  \"file\": \"https:\\\\MoleculeMan.com\",\n" +
//                "  \"author\": \"Molecule Man\",\n" +
//                "  \"image\": \"https://sun9-west.userapi.com/sun9-15/s/v1/ig2/LW4GhqusrVZRkCaztykRLwRMwUw78EB2vPDn5MW4YnJ57K85MDpOPcyVd_i0tfLh-9z-vpIac9z-RYcpkpKwpVTB.jpg?size=1620x2160&quality=95&type=album\",\n" +
//                "  \"kind\": \"rok\"\n" +
//                "},"+
//                        "{\n" +
//                        "  \"id\": \"100\",\n" +
//                        "  \"name\": \"Molecule\",\n" +
//                        "  \"file\": \"https:\\\\MoleculeMan.com\",\n" +
//                        "  \"author\": \"Molecule Man\",\n" +
//                        "  \"image\": \"image.png\",\n" +
//                        "  \"kind\": \"rok\"\n" +
//                        "},"+
//                        "{\n" +
//                        "  \"id\": \"100\",\n" +
//                        "  \"name\": \"Molecule\",\n" +
//                        "  \"file\": \"https:\\\\MoleculeMan.com\",\n" +
//                        "  \"author\": \"Molecule Man\",\n" +
//                        "  \"image\": \"image.png\",\n" +
//                        "  \"kind\": \"rok\"\n" +
//                        "},"+
//                        "{\n" +
//                        "  \"id\": \"100\",\n" +
//                        "  \"name\": \"Molecule\",\n" +
//                        "  \"file\": \"https:\\\\MoleculeMan.com\",\n" +
//                        "  \"author\": \"Molecule Man\",\n" +
//                        "  \"image\": \"image.png\",\n" +
//                        "  \"kind\": \"rok\"\n" +
//                        "},"+
//                        "{\n" +
//                        "  \"id\": \"100\",\n" +
//                        "  \"name\": \"Molecule\",\n" +
//                        "  \"file\": \"https:\\\\MoleculeMan.com\",\n" +
//                        "  \"author\": \"Molecule Man\",\n" +
//                        "  \"image\": \"image.png\",\n" +
//                        "  \"kind\": \"rok\"\n" +
//                        "}"+
//                "]";

// Gson gson = new Gson();

// ArrayList<MusicFile> musicFiles = gson.fromJson(responseJSON, new TypeToken<ArrayList<MusicFile>>(){}.getType());


