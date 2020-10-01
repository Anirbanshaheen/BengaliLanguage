package com.example.bengalilanguage;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentRelationBarisal extends Fragment {

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

    public FragmentRelationBarisal() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_relation_barisal, container, false);

        // Create for audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word(R.drawable.father, "বাবা", "আব্বা", R.raw.father));
        words.add(new Word(R.drawable.aunt, "মা", "আম্মা", R.raw.amma));
        words.add(new Word(R.drawable.fufa, "চাচা", "দুদু", R.raw.chacha));
        words.add(new Word(R.drawable.mother, "চাচি", "চাচি", R.raw.chachi));
        words.add(new Word(R.drawable.sister, "বোন", "আফা", R.raw.sister));
        words.add(new Word(R.drawable.khalu, "খালা", "খালা", R.raw.khala));
        words.add(new Word(R.drawable.kalu, "খালু", "খালু", R.raw.khalu));
        words.add(new Word(R.drawable.uncle, "দুলাভাই", "দুলাভাই", R.raw.dulabhai));
        words.add(new Word(R.drawable.khalu, "ফুফু", "ফুফু", R.raw.fufu));
        words.add(new Word(R.drawable.kalu, "ফুফা", "ফুফা", R.raw.fufa));
        words.add(new Word(R.drawable.brother, "ভাই", "ভাইয়া", R.raw.brother));
        words.add(new Word(R.drawable.vabi, "ভাবি", "ভাবি", R.raw.vabi));
        words.add(new Word(R.drawable.mama, "মামা", "মামা", R.raw.mama));
        words.add(new Word(R.drawable.mami, "মামি", "মামি", R.raw.mami));

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
