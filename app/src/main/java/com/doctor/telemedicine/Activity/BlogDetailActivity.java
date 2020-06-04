package com.doctor.telemedicine.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doctor.telemedicine.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.doctor.telemedicine.Data.Data.NOW_SHOWING_BLOG;
import static com.doctor.telemedicine.Data.Data.PHOTO_BASE;

public class BlogDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youtube_player_view;
    @BindView(R.id.tv_body)
    TextView tv_body;
    @BindView(R.id.coverImage)
    ImageView coverImage;
    Context context = this;
    @BindView(R.id.linearVdoBody)
    LinearLayout linearVdoBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        ButterKnife.bind(this);
        tv_title.setText(NOW_SHOWING_BLOG.getTitle());
        tv_body.setText(NOW_SHOWING_BLOG.getBody());
        if (NOW_SHOWING_BLOG.getPhotoInfo().size() > 0) {

            Glide.with(context).load(PHOTO_BASE + NOW_SHOWING_BLOG.getPhotoInfo().get(0).getPhoto()).into(coverImage);
        }
        if (NOW_SHOWING_BLOG.getYoutube_video() != null && NOW_SHOWING_BLOG.getYoutube_video().length() > 0) {
            linearVdoBody.setVisibility(View.VISIBLE);
            coverImage.setVisibility(View.GONE);
            youtube_player_view.setEnableAutomaticInitialization(false);
            youtube_player_view.initialize(new YouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    if (NOW_SHOWING_BLOG.getYoutube_video().contains("youtube.com")) {

                        youTubePlayer.loadVideo(NOW_SHOWING_BLOG.getYoutube_video().replace("https://www.youtube.com/watch?v=", ""), 0);


                    }else  if (NOW_SHOWING_BLOG.getYoutube_video().contains("youtu.be")) {

                        youTubePlayer.loadVideo(NOW_SHOWING_BLOG.getYoutube_video().replace("https://youtu.be/", ""), 0);


                    }
                }

                @Override
                public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

                }

                @Override
                public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

                }

                @Override
                public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

                }

                @Override
                public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

                }

                @Override
                public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(YouTubePlayer youTubePlayer, String s) {

                }

                @Override
                public void onApiChange(YouTubePlayer youTubePlayer) {

                }
            });
        } else {
            linearVdoBody.setVisibility(View.GONE);
            coverImage.setVisibility(View.VISIBLE);


        }

    }

    public void Back(View view) {
        onBackPressed();
    }
}
