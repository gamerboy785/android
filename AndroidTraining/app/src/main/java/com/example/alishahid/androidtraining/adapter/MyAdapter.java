package com.example.alishahid.androidtraining.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alishahid.androidtraining.R;
import com.example.alishahid.androidtraining.controller.DetailActivity;
import com.example.alishahid.androidtraining.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{

    private List<Item> items;
    private Context context;

    public MyAdapter(List<Item> items, Context context)
    {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position)
    {

        Item currentItem = items.get(position);

        Picasso.with(context)
                .load(currentItem.getAvatarUrl())
                .placeholder(R.drawable.loading)
                .into(holder.userImage);

        holder.userLogin.setText(currentItem.getLogin());

    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.user_image)
        ImageView userImage;

        @BindView(R.id.user_login)
        TextView userLogin;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION)
                    {
                        Item clickedItem = items.get(position);
                        //Toast.makeText(context,"You clicked on" + " " + clickedItem.getLogin(),Toast.LENGTH_SHORT).show();

                        //Make call to detail activity with user data to show

                        Intent detailActivity = new Intent(context, DetailActivity.class);

                        detailActivity.putExtra("avatar_url",clickedItem.getAvatarUrl());
                        detailActivity.putExtra("login",clickedItem.getLogin());
                        detailActivity.putExtra("html_url",clickedItem.getHtmlUrl());
                        detailActivity.putExtra("score",clickedItem.getScore());

                        context.startActivity(detailActivity);


                    }


                }
            });

        }
    }

}
