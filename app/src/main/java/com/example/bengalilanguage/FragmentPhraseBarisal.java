package com.example.bengalilanguage;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

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
public class FragmentPhraseBarisal extends Fragment {

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


    public FragmentPhraseBarisal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phrase_barisal, container, false);

        // Create for audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("আপনি কেমন আছেন ?", "এ গেদু কেবিল আছ", R.raw.how_are_you));
        words.add(new Word("আমি ভাল আছি", "মুইত একসের ভাল আছি", R.raw.i_am_fine));
        words.add(new Word("আপনি থাকেন কোথায় ?", "আমনে থায়েন কোথায়", R.raw.where_do_you_live));
        words.add(new Word("আমি বরিশালে থাকি", "মুই বরিশালে থাকি", R.raw.i_live_in_barisal));
        words.add(new Word("আপনি কি করেন ?", "আমনে হরেন কি", R.raw.what_do_you_do));
        words.add(new Word("আপনি কি খেয়েছেন ?", "আমনে কি খাইছেন", R.raw.what_did_you_eat));
        words.add(new Word("আপনার নাম কি ?", "আমনের নাম কি", R.raw.what_is_your_name));
        words.add(new Word("আপনার বয়স কত ?", "আমনের বয়স কত", R.raw.how_old_are_you));
        words.add(new Word("ভাল থাকবেন", "ভাল থাইক্কেন কইলুম", R.raw.stay_well));

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
