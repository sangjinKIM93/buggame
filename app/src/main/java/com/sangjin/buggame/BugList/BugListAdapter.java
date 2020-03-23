package com.sangjin.buggame.BugList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sangjin.buggame.R;
import com.sangjin.buggame.Room.Bug;

import java.util.ArrayList;

public class BugListAdapter extends RecyclerView.Adapter<BugListAdapter.CustomViewHolder> {

    private ArrayList<Bug> mList=null;
    private Context context=null;

    public BugListAdapter(ArrayList<Bug> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView item_image;
        protected TextView item_name, item_address, item_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_address = itemView.findViewById(R.id.item_address);
            item_date = itemView.findViewById(R.id.item_date);
        }

    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bug, parent, false);
        CustomViewHolder viewHolder=new CustomViewHolder(view);
        context=parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        String addressEdit = mList.get(position).getAddress().replace("대한민국", "");

        holder.item_image.setImageResource(mList.get(position).getBugImg());
        holder.item_name.setText(mList.get(position).getBugName());
        holder.item_address.setText(addressEdit);
        holder.item_date.setText(mList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return (null!=mList?mList.size():0);
    }

}
