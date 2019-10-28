package com.example.pegsolitaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    BoardAdapter m_adapter;
    TextView gameStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameStatus = findViewById(R.id.gameStatus);

        BoardItem[] states = new BoardItem[16];

        for(int i=0; i<= 15 ; i++) {
            states[i] = new BoardItem(true);
        }

        Random random = new Random();
        states[ random.nextInt( 16)].setPeg(false);


        final RecyclerView recyclerView = findViewById(R.id.boardView);
        m_adapter = new BoardAdapter(this, states, recyclerView.getHeight());
        recyclerView.setAdapter(m_adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new
             ViewTreeObserver.OnGlobalLayoutListener() {
                 @Override
                 public void onGlobalLayout() {
                     //don't forget remove this listener
                     recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                     //get item height here
                     int itemHeight = recyclerView.getHeight();
                     m_adapter.setTotalHeight(itemHeight);
                     m_adapter.notifyDataSetChanged();
                 }
             });
    }

    public void newGame(View v) {
        BoardItem[] states = m_adapter.getStates();
        for( BoardItem item : states) {
            item.setPeg(true);
        }
        gameStatus.setText("");
        Random random = new Random();
        states[ random.nextInt( 16)].setPeg(false);
        m_adapter.setSelectedIndex(-1);
        m_adapter.notifyDataSetChanged();
    }

    public void startImageSelect(View v) {
        Intent intent=new Intent(MainActivity.this,ImageSelectActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            int imageId = data.getIntExtra("IMAGE_ID", 0);
            m_adapter.setImageId(imageId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        boolean[] states = new boolean[16];

        for(int i=0 ;i < m_adapter.getStates().length ; i++) {
            states[i] = m_adapter.getStates()[i].isPeg;
        }

        savedInstanceState.putBooleanArray("states", states);
        savedInstanceState.putInt("selectedIndex", m_adapter.getSelectedIndex());

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        boolean[] states = savedInstanceState.getBooleanArray("states");
        int selectedIndex = savedInstanceState.getInt("selectedIndex");
        BoardItem[] stateArr = m_adapter.getStates();
        for(int i=0 ;i < states.length ; i++) {
            stateArr[i].setPeg(states[i]);
        }
        m_adapter.setSelectedIndex(selectedIndex);
        m_adapter.notifyDataSetChanged();
    }
}
