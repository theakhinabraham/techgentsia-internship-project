package com.theakhinabraham.birthdaywishingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WishActivity extends AppCompatActivity {

    TextView wishTo, wishFrom;
    Button backBtn;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        wishTo = findViewById(R.id.wishTo);
        wishFrom = findViewById(R.id.wishFrom);
        backBtn = findViewById(R.id.backBtn);
        imageView1 = findViewById(R.id.imageView1);

        Intent intent = getIntent();
        String toName = intent.getStringExtra("to_name");
        String fromName = intent.getStringExtra("from_name");

        //TODO: Check for intent receiving mistakes (Uri/ArrayList/List/String) --------------------------------
        ArrayList<String> imageUris = intent.getStringArrayListExtra("image_uris");

        wishTo.setText("Happy Birthday " + toName);
        wishFrom.setText("Message from " + fromName);

        LinearLayout layout = findViewById(R.id.layoutWish);

        //Add images in loop
        //TODO: Add loop to add ImageViews --------------------------------
        //loop ends here

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WishActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}