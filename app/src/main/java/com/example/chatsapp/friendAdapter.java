package com.example.chatsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsapp.databinding.RowConvirsationBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class friendAdapter extends RecyclerView.Adapter<friendAdapter.friendViewHolder> {

    Context context;
    ArrayList<User>friend;

    public friendAdapter(Context context, ArrayList<User> friend) {
        this.context = context;
        this.friend = friend;
    }

    @NonNull
    @NotNull
    @Override
    public friendViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_convirsation,parent,false);
        return new friendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull friendAdapter.friendViewHolder holder, int position) {
User user=friend.get(position);
holder.binding.username.setText(user.getName());
Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.avater).into(holder.binding.listImage);

    }

    @Override
    public int getItemCount() {
        return friend.size();
    }

    public class friendViewHolder extends RecyclerView.ViewHolder{
         RowConvirsationBinding binding;
        public friendViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding=RowConvirsationBinding.bind(itemView);
        }
    }
}
