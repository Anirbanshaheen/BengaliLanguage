package com.example.bengalilanguage;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFoodBarisal extends Fragment {

    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                        releaseMediaPlayer();
                    }
                }
            };

    // For reusing, it create only once
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    public FragmentFoodBarisal() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_food_barisal, container, false);

        // Create for audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word(R.drawable.hog_plum, "আমড়া", "আমড়া", R.raw.hog_plum));
        words.add(new Word(R.drawable.coconut, "নারিকেল", "নারিকেল", R.raw.coconut));
        words.add(new Word(R.drawable.guava, "পেয়ারা", "পেয়ারা", R.raw.guava));
        words.add(new Word(R.drawable.betelnut, "সুপারি", "সুপারি", R.raw.betel_nut));
        words.add(new Word(R.drawable.spinach, "শাক", "শাক", R.raw.spinach));
        words.add(new Word(R.drawable.cow, "গরু", "গরু", R.raw.cow));
        words.add(new Word(R.drawable.goat, "ছাগল", "ছাগল", R.raw.goat));
        words.add(new Word(R.drawable.buffalo, "মহিষ", "মহিষ", R.raw.buffalo));
        words.add(new Word(R.drawable.fish, "মাছ", "মাছ", R.raw.fish));
        words.add(new Word(R.drawable.chicken, "মুরগি", "মুরগি", R.raw.chicken));
        words.add(new Word(R.drawable.pulses, "ডাল", "ডাল", R.raw.pulses));
        words.add(new Word(R.drawable.rice, "ভাত", "ভাত", R.raw.rice));
        words.add(new Word(R.drawable.curd, "দই", "দই", R.raw.curd));
        words.add(new Word(R.drawable.sweet, "মিষ্টি", "মিষ্টি", R.raw.sweet));

        mRecyclerView = rootView.findViewById(R.id.recyclerView_list);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new WordAdapter(words);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Word word = words.get(position);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,

                        AudioManager.STREAM_MUSIC,
                        // For short period of time
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

//                    mAudioManager.registerMediaButtonEventReceiver();

                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);

//                words.get(position);
//                if(position == 0){
//                    mediaPlayer = MediaPlayer.create(Number.this,R.raw.buzzer);
//                    mediaPlayer.start();
//                }else if(position == 1){
//                    mediaPlayer = MediaPlayer.create(Number.this,R.raw.crickets);
//                    mediaPlayer.start();
//                }

                }
            }
        });

        return rootView;
    }

    // For efficient use of resource, when we press the home button or back button then it should be stopped.
    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    // For efficient memory management
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {

            mediaPlayer.release();
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
