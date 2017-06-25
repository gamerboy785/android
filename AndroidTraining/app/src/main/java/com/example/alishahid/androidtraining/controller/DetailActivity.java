package com.example.alishahid.androidtraining.controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.alishahid.androidtraining.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity
{
    @BindView(R.id.user_image)
    ImageView userImage;

    @BindView(R.id.user_login)
    TextView userLogin;

    @BindView(R.id.user_link)
    TextView githubLink;

    @BindView(R.id.user_score)
    RatingBar userScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        //get user data passed from the list_item click listener
        Bundle extras = getIntent().getExtras();

        String avatar_url = extras.getString("avatar_url");
        String login = extras.getString("login");
        String html_url = extras.getString("html_url");
        Double score = extras.getDouble("score");

        //display user info
        Picasso.with(this)
                .load(avatar_url)
                .placeholder(R.drawable.loading)
                .into(userImage);

        userLogin.setText(login);
        githubLink.setText(html_url);

        Linkify.addLinks(githubLink,Linkify.WEB_URLS);

        userScore.setRating(score.floatValue());

    }

}
