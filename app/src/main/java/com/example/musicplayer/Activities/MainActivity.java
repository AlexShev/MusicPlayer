package com.example.musicplayer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.musicplayer.Senders.AuthorsMusicListLoader;
import com.example.musicplayer.ContentController;
import com.example.musicplayer.Data.ArtistFile;
import com.example.musicplayer.Data.MusicFile;
import com.example.musicplayer.Senders.MusicLoader;
import com.example.musicplayer.R;
import com.example.musicplayer.musicService.MusicRepository;

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

       // ContentController.observRepository(this);

        adapterRecommendedTrack.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ContentController.ListType currType = ContentController.getCurrType();

                        if (currType != ContentController.ListType.recommendedTrack){
                            ContentController.setCurrType(ContentController.ListType.recommendedTrack);
                            MusicRepository musicRepository = new MusicRepository(ContentController.getRecommendedTracks());
                            musicRepository.setCurrent(position);
                            ContentController.Repository.setValue(musicRepository);
                        }
                        else
                        {
                            MusicRepository musicRepository = ContentController.Repository.getValue();
                            // здесь надо делать проверку на текущую позицию
                            if (musicRepository.getCurrentIndex() != position)
                            {
                                musicRepository.setCurrent(position);
                            }
                        }

                        startActivity(new Intent(MainActivity.this, PlayMusicActivity.class));

                        Toast.makeText(MainActivity.this, "You clicked " + adapterRecommendedTrack.getItemInfo(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        adapterRecommendedAuthors.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ContentController.ListType currType = ContentController.getCurrType();

                        if (currType != ContentController.ListType.recommendedAuthors){
                            ContentController.setCurrType(ContentController.ListType.recommendedAuthors);

                        }

                        MusicRepository musicRepository = new MusicRepository(ContentController.getAuthorsTracks());
                        musicRepository.setCurrent(0);

                        // ContentController.Repository.setValue(musicRepository);
                        adapterRecommendedAuthors.getItem(position).setActive(true);
                        new AuthorsMusicListLoader(MainActivity.this, adapterRecommendedAuthors.getItem(position).getId()).start();

                        startActivity(new Intent(MainActivity.this, AuthorTracksListActivity.class));

                        Toast.makeText(MainActivity.this, "You clicked " + adapterRecommendedAuthors.getItemInfo(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        adapterPopularTrack.setClickListener(
                new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "You clicked " + adapterPopularTrack.getItemInfo(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(MainActivity.this, RecommendedTrackListActivity.class));
                    }
                }
        );

        findViewById(R.id.more_recommendedAuthors).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, RecommendedAuthorListActivity.class));
                    }
                }
        );

        findViewById(R.id.more_popularTrack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, PopularTrackListActivity.class));
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


