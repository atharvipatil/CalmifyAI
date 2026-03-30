package com.example.ai;   // ✅ FIXED

import android.content.Intent;   // ✅ ADDED
import android.net.Uri;         // ✅ ADDED
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private List<MusicTrack> musicTracks;
    private int currentTrackIndex = 0;
    private boolean isPlaying = false;

    private TextView tvTrackTitle, tvTrackDuration, tvCurrentTime;
    private ImageButton btnPlayPause, btnNext, btnPrevious;
    private SeekBar seekBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music, container, false);

        initViews(view);
        initMusicTracks();
        setupListeners();
        loadTrack(currentTrackIndex);

        return view;
    }

    private void initViews(View view) {
        tvTrackTitle = view.findViewById(R.id.tvTrackTitle);
        tvTrackDuration = view.findViewById(R.id.tvTrackDuration);
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime);
        btnPlayPause = view.findViewById(R.id.btnPlayPause);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        seekBar = view.findViewById(R.id.seekBar);

        CardView cardRelaxing = view.findViewById(R.id.cardRelaxing);
        CardView cardNature = view.findViewById(R.id.cardNature);
        CardView cardMeditation = view.findViewById(R.id.cardMeditation);

        cardRelaxing.setOnClickListener(v -> Toast.makeText(getContext(), "🎵 Relaxing Music", Toast.LENGTH_SHORT).show());
        cardNature.setOnClickListener(v -> Toast.makeText(getContext(), "🌿 Nature Sounds", Toast.LENGTH_SHORT).show());
        cardMeditation.setOnClickListener(v -> Toast.makeText(getContext(), "🧘 Meditation Music", Toast.LENGTH_SHORT).show());
    }

    private void initMusicTracks() {
        musicTracks = new ArrayList<>();

        musicTracks.add(new MusicTrack("Peaceful Piano", "Relaxing Music", "https://www.youtube.com/watch?v=3jWRrafhO7M"));
        musicTracks.add(new MusicTrack("Ocean Waves", "Nature Sounds", "https://www.youtube.com/watch?v=V1bFr2SWP1I"));
        musicTracks.add(new MusicTrack("Rain Sounds", "Nature Sounds", "https://www.youtube.com/watch?v=nDq6TstdEi8"));
        musicTracks.add(new MusicTrack("Forest Meditation", "Meditation", "https://www.youtube.com/watch?v=d1k2SKiq07w"));
    }

    private void setupListeners() {
        btnPlayPause.setOnClickListener(v -> {
            if (isPlaying) {
                pauseMusic();
            } else {
                playMusic();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentTrackIndex < musicTracks.size() - 1) {
                currentTrackIndex++;
                loadTrack(currentTrackIndex);
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentTrackIndex > 0) {
                currentTrackIndex--;
                loadTrack(currentTrackIndex);
            }
        });
    }

    private void loadTrack(int index) {
        MusicTrack track = musicTracks.get(index);
        tvTrackTitle.setText(track.getTitle());
    }

    private void playMusic() {
        MusicTrack track = musicTracks.get(currentTrackIndex);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(track.getUrl()));
        startActivity(intent);

        isPlaying = true;
        btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
    }

    private void pauseMusic() {
        isPlaying = false;
        btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    static class MusicTrack {
        private String title;
        private String category;
        private String url;

        public MusicTrack(String title, String category, String url) {
            this.title = title;
            this.category = category;
            this.url = url;
        }

        public String getTitle() { return title; }
        public String getCategory() { return category; }
        public String getUrl() { return url; }
    }
}