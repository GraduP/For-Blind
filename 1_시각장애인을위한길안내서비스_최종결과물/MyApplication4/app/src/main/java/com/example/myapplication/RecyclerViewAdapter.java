package com.example.myapplication;

import static com.example.myapplication.tts.SingleTonTTS.tts;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.tts.SingleTonTTS;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<RecyclerViewItem> mData = null;
    private ViewHolder holder;
    private long delay = 0; // 더블클릭용
    SingleTonTTS tts;
    String endX;
    String endY;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView routeNm;
        private TextView station;
        private TextView transferNm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            time = itemView.findViewById(R.id.time);
            routeNm = itemView.findViewById(R.id.routeNm);
            station = itemView.findViewById(R.id.station);
            transferNm = itemView.findViewById(R.id.transferNm);



        }
    }

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> data) {
        mData = data;
    }

    @NonNull
    @Override //viewholder 객체 생성
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_item,parent,false);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);
        tts = tts.getInstance();
        tts.init(view.getContext());
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        RecyclerViewItem item = mData.get(position);
        holder.time.setText( item.getTime() + "분"); //정류소 이름
        holder.station.setText(item.getStation());
        holder.transferNm.setText("탑승 횟수 : "+ String.valueOf(item.getTransferItems().get(0).getPathItemList().size()));
        holder.routeNm.setText(item.getTransferItems().get(0).getPathItemList().get(0).getRouteNm() );

        holder.itemView.setOnClickListener(new View.OnClickListener() { //다음 액티비티로 넘어감
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis() > delay){
                    delay = System.currentTimeMillis() + 300;
                    tts.stop();
                    tts.speak(mData.get(holder.getAdapterPosition()).getTransferItems().get(0).getPathItemList().get(0).getRouteNm() + "번 버스를" +
                            mData.get(holder.getAdapterPosition()).getStation() + "에서 타고 이동합니다." + "버스 탑승 시간은" + mData.get(holder.getAdapterPosition()).getTime()
                            +"분 정도 소요되며 버스 탑승 횟수는" + mData.get(holder.getAdapterPosition()).getTransferItems().get(0).getPathItemList().size() + "번 입니다" );
                    return;
                }

                if(System.currentTimeMillis() <= delay) {
                    tts.stop();
                    Intent intent =  new Intent(view.getContext(), GeoActivity.class);
                    intent.putExtra("endX" , endX);
                    intent.putExtra("endY", endY);
                    intent.putExtra("time" , mData.get(holder.getAdapterPosition()).getTime());
                    intent.putExtra("station" , mData.get(holder.getAdapterPosition()).getStation());
                    intent.putExtra("transferItem", mData.get(holder.getAdapterPosition()).getTransferItems().get(0)); //이게 대부분이 정보를 갖고 있음
                    view.getContext().startActivity(intent);

                }

            }
        });
    }

    // getItemCount : 전체 데이터의 개수를 리턴
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getEndX() {
        return endX;
    }

    public String getEndY() {
        return endY;
    }

    public void setEndX(String endX) {
        this.endX = endX;
    }

    public void setEndY(String endY) {
        this.endY = endY;
    }
}
