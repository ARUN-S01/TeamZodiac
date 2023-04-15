package com.example.teamzodiac;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle App) {

        super.onCreate(App);
        setContentView(R.layout.activity_video);

        VideoView vv = (VideoView) findViewById(R.id.VideoView);
        String uri = "android.resource://"
                + getPackageName() + "/" + R.raw.tce_viedo;
        vv.setVideoURI(Uri.parse(uri));
        vv.start();

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }
}
