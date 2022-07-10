package com.theakhinabraham.birthdaywishingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText to, from;
    Button create, loadImg;

    int PICK_IMAGE_MULTIPLE = 1;
    ArrayList <String> imageEncodedList;
    String imageEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        create = findViewById(R.id.create);
        loadImg = findViewById(R.id.loadImg);

        loadImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        loadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgChooser();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toName = to.getText().toString();
                String fromName = from.getText().toString();

                //TODO: check intent for URI (ArrayList/List/String) mistakes --------------------------------
                ArrayList<String> imageUris = imageEncodedList;

                Intent intent = new Intent(MainActivity.this, WishActivity.class);

                intent.putExtra("to_name", toName);
                intent.putExtra("from_name", fromName);
                intent.putStringArrayListExtra("image_uris", (ArrayList<String>) imageUris);

                startActivity(intent);
            }
        });
    }

    void imgChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);      //Enabled multiple image selection
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    //TODO: Check code below (selecting images) --------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data){
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imageEncodedList = new ArrayList<String>();
                if(data.getData() != null){
                    Uri mImageUri = data.getData();

                    Cursor cursor = getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();
                }
                else {
                    if(data.getClipData() != null){
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for(int i = 0; i < mClipData.getItemCount(); i++){
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                            Cursor cursor = getContentResolver().query(uri,filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imageEncodedList.add(imageEncoded);
                            cursor.close();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            }
            else {
                Toast.makeText(this, "You haven't selected any images", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}