package com.example.myapplication;

import com.example.myapplication.tts.SingleTonTTS;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SearchRecyclerViewItem> mData = null;
    private ViewHolder holder;
    private long delay = 0;
    SingleTonTTS tts;
    String startX;
    String startY;


    @NonNull
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchviewholder,parent,false);
        SearchRecyclerViewAdapter.ViewHolder viewHolder = new SearchRecyclerViewAdapter.ViewHolder(view);
        tts = tts.getInstance();
        tts.init(view.getContext());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchRecyclerViewItem item = mData.get(position);
        holder.title.setText(item.getTitle());
        //holder.searchIcon.setImageResource(item.getReourcesId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.stop();
                    tts.speak(mData.get(holder.getAdapterPosition()).getTitle());
                    return;
                }
                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    Intent intent = new Intent(view.getContext(), MainActivity2.class);
                    intent.putExtra("title", mData.get(holder.getAdapterPosition()).getTitle());
                    intent.putExtra("startX", startX);
                    intent.putExtra("startY", startY);
                    intent.putExtra("endX", mData.get(holder.getAdapterPosition()).getX());
                    intent.putExtra("endY", mData.get(holder.getAdapterPosition()).getY());
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조
            title = itemView.findViewById(R.id.title);
        }
    }

    public SearchRecyclerViewAdapter(ArrayList<SearchRecyclerViewItem> data) {
        mData = data;
    }

    public String getStartX() {
        return startX;
    }

    public String getStartY() {
        return startY;
    }

    public void setStartX(String startX) {
        this.startX = startX;
    }

    public void setStartY(String startY) {
        this.startY = startY;
    }
}
