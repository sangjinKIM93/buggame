package com.sangjin.buggame.BugList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sangjin.buggame.R;
import com.sangjin.buggame.RegisterBugActivity;
import com.sangjin.buggame.Room.AppDatabase;
import com.sangjin.buggame.Room.Bug;
import com.sangjin.buggame.Room.BugDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BugListActivity extends AppCompatActivity {

    private ArrayList<Bug> mArrayList;
    private BugListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_list);

        mArrayList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_bugList);

        db = Room.databaseBuilder(BugListActivity.this, AppDatabase.class, "bug-db")
                .build();

        //roomDB CRUD는 thread로 처리해줘야함
        new Thread(new Runnable() {
            @Override
            public void run() {
                mArrayList = (ArrayList<Bug>) db.bugDao().getAllList();
                mAdapter = new BugListAdapter(mArrayList, BugListActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(BugListActivity.this));

            }
        }).start();


    }


}
