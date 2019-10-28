package com.example.pegsolitaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ImageSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
    }

    public void onClickMinion(View V) {
        Intent intent=new Intent();
        intent.putExtra("IMAGE_ID",R.drawable.bullyminion);
        setResult(2,intent);
        finish();//finishing activity
    }

    public void onClickTentacles(View V) {
        Intent intent=new Intent();
        intent.putExtra("IMAGE_ID",R.drawable.tentacles);
        setResult(2,intent);
        finish();//finishing activity
    }

    public void onClickVileFluid(View V) {
        Intent intent=new Intent();
        intent.putExtra("IMAGE_ID",R.drawable.vilefluid);
        setResult(2,intent);
        finish();//finishing activity
    }

    public void onClickVileMummy(View V) {
        Intent intent=new Intent();
        intent.putExtra("IMAGE_ID",R.drawable.mummy);
        setResult(2,intent);
        finish();//finishing activity
    }

    public void onClickVileGhost(View V) {
        Intent intent=new Intent();
        intent.putExtra("IMAGE_ID",R.drawable.ghost);
        setResult(2,intent);
        finish();//finishing activity
    }
}
