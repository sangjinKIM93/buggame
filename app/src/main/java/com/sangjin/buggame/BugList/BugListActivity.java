package com.sangjin.buggame.BugList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sangjin.buggame.MapActivity;
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
    private FloatingActionButton fab_map;

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
                mRecyclerView.setLayoutManager(new GridLayoutManager(BugListActivity.this, 2));

                //아이템간 간격 조절, addItemDecoration을 통해서 recyclerview item의 간격 조절, 구분선 추가 등의 작업을 할 수 있어.
                mRecyclerView.addItemDecoration(new GridItemSpan(2, 20, true, 0));
            }
        }).start();


        fab_map = findViewById(R.id.fab_map);
        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BugListActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }


}
