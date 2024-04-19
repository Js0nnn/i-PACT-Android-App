package com.ipact.ipact_23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context context;
    private ArrayList<User> originalList;
    private ArrayList<User> filteredList;

    public MyAdapter2(Context context, ArrayList<User> list) {
        this.context = context;
        this.originalList = list;
        this.filteredList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_allpapers, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = filteredList.get(position);
        holder.paperTitle.setText(user.getPaperTitle());
        holder.paperID.setText(user.getPaperID());
        holder.paperAuthor.setText(user.getAuthor());
        holder.paperDate.setText(user.getDate());
        holder.paperStat.setText(user.getStatus());
        holder.paperMode.setText(user.getMode());
        holder.paperSesTitle.setText(user.getSessionTitle());
        holder.paperVenue.setText(user.getVenue());
        holder.paperTime.setText(user.getTime());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filterList(ArrayList<User> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView paperTitle, paperID, paperAuthor, paperDate, paperStat, paperMode, paperSesTitle, paperVenue, paperTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            paperTitle = itemView.findViewById(R.id.paperTitle);
            paperID = itemView.findViewById(R.id.paperID);
            paperAuthor = itemView.findViewById(R.id.paperAuthor);
            paperDate = itemView.findViewById(R.id.paperDate);
            paperStat = itemView.findViewById(R.id.paperStat);
            paperMode = itemView.findViewById(R.id.paperMode);
            paperSesTitle = itemView.findViewById(R.id.paperSessionTitle);
            paperVenue = itemView.findViewById(R.id.paperVenue);
            paperTime = itemView.findViewById(R.id.paperTime);
        }
    }
}
