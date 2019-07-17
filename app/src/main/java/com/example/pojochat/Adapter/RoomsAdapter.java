package com.example.pojochat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pojochat.Model.ChatRoom;
import com.example.pojochat.R;

import java.util.List;


public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    List<ChatRoom> rooms;

    public RoomsAdapter(List<ChatRoom> rooms) {
        this.rooms = rooms;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_chat_list,viewGroup,false);
        return new ViewHolder(view);
    }

    public void ChangeData(List<ChatRoom> newRooms){
        this.rooms=newRooms;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {

       final ChatRoom room=rooms.get(pos);

        viewHolder.name.setText(room.getName());
        viewHolder.desc.setText(room.getDesc());
        //viewHolder.imageView.
        if(onItemClickListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(pos,room);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(rooms==null)return 0;
        return rooms.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView desc;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.desc);
            imageView=itemView.findViewById(R.id.groupImage);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos, ChatRoom room);
    }

}
