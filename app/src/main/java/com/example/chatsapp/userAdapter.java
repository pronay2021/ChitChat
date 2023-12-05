package com.example.chatsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatsapp.databinding.RowforaddingBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {
    Context context;
    ArrayList <User>users;
    RecyclerViewClickListener listener;


    public userAdapter(Context context, ArrayList<User> user,RecyclerViewClickListener listener) {
        this.context = context;
        this.users = user;
        this.listener=listener;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rowforadding,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull userAdapter.userViewHolder holder, int position) {
        User user= users.get(position);
        holder.binding.username.setText(user.getName());
        holder.binding.phnNum.setText(user.getPhoneNumber());
        Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.avater).into(holder.binding.listImage);
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RowforaddingBinding binding;

        public userViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            binding= RowforaddingBinding.bind(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
