//package com.sangjin.buggame.BugList;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.ViewModel;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.room.Room;
//
//import com.sangjin.buggame.Room.AppDatabase;
//import com.sangjin.buggame.Room.Bug;
//
//import java.util.ArrayList;
//
//public class BugViewModel extends AndroidViewModel {
//
//    private AppDatabase db;
//
//    public BugViewModel(@NonNull Application application) {
//        super(application);
//        db = Room.databaseBuilder(application, AppDatabase.class, "bug-db")
//                .build();
//    }
//
//    public void getData(){
//
//        //roomDB CRUD는 thread로 처리해줘야함
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mArrayList = (ArrayList<Bug>) db.bugDao().getAllList();
//                mAdapter = new BugListAdapter(mArrayList, BugListActivity.this);
//                mRecyclerView.setAdapter(mAdapter);
//                mRecyclerView.setLayoutManager(new LinearLayoutManager(BugListActivity.this));
//
//            }
//        }).start();
//    }
//}
