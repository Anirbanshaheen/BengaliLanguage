package com.example.bengalilanguage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {

    public ArrayList<Word> mWords;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class WordHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextViewLocal;
        public TextView mTextViewBangali;

        public WordHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.identity_image);
            mTextViewLocal = itemView.findViewById(R.id.local_text);
            mTextViewBangali = itemView.findViewById(R.id.bangali_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public WordAdapter(ArrayList<Word> words){
        mWords = words;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        WordHolder wordHolder = new WordHolder(view,mListener);
        return wordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        Word currentItem = mWords.get(position);

//        holder.mImageView.setImageResource(currentItem.getmImage());
//        holder.mTextViewLocal.setText(currentItem.getmLocalName());
//        holder.mTextViewEnglish.setText(currentItem.getmEnglishName());

        if(currentItem.hasImage()){
            holder.mImageView.setImageResource(currentItem.getmImage());
            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mTextViewLocal.setText(currentItem.getmLocalName());
            holder.mTextViewBangali.setText(currentItem.getmBangaliName());
        }else {
            holder.mImageView.setVisibility(View.GONE);
            holder.mTextViewLocal.setText(currentItem.getmLocalName());
            holder.mTextViewBangali.setText(currentItem.getmBangaliName());

        }
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    //    WordAdapter(Activity context, ArrayList<Word> words) {
//        super(context, 0, words);
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        Word currentWord = getItem(position);
//
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
//        }
//
//        TextView local = listItemView.findViewById(R.id.loaclLanguage);
//        local.setText(currentWord.getmLocalName());
//
//        TextView english = listItemView.findViewById(R.id.englishLanguage);
//        english.setText(currentWord.getmEnglishName());
//
//        return listItemView;
//    }

}
