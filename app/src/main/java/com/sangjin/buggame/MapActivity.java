package com.sangjin.buggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.sangjin.buggame.BugList.BugListActivity;
import com.sangjin.buggame.Room.AppDatabase;
import com.sangjin.buggame.Room.Bug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    LinearLayout linear_info;
    TextView tv_infoName, tv_infoAddress, tv_infoDate;
    ImageView iv_infoImg;
    Button btn_exitInfo;

    private AppDatabase db;

    private ArrayList<Bug> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        linear_info = findViewById(R.id.linear_info);
        tv_infoName = findViewById(R.id.tv_infoName);
        tv_infoAddress = findViewById(R.id.tv_infoAddress);
        tv_infoDate = findViewById(R.id.tv_infoDate);
        iv_infoImg = findViewById(R.id.iv_infoImg);
        btn_exitInfo = findViewById(R.id.btn_exitInfo);

        //네이버 지도 초기 선언
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);


        db = Room.databaseBuilder(MapActivity.this, AppDatabase.class, "bug-db")
                .allowMainThreadQueries()
                .build();

        mArrayList = (ArrayList<Bug>) db.bugDao().getAllList();


        btn_exitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_info.setVisibility(View.INVISIBLE);
                btn_exitInfo.setVisibility(View.INVISIBLE);
            }
        });


    }

    InfoWindow infoWindow;
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        List<Marker> markers = new ArrayList<>();
        Log.d("ArrayList : ", String.valueOf(mArrayList));

        for(int i=0; i<mArrayList.size(); i++){

            Marker marker = new Marker();
            marker.setPosition(new LatLng(mArrayList.get(i).getLatitude(), mArrayList.get(i).getLongitude()));
            marker.setHideCollidedMarkers(false);
            marker.setTag(mArrayList.get(i).getBugName());
            //marker.setIcon(OverlayImage.fromResource(mArrayList.get(i).getBugImg()));
            markers.add(marker);

        }

        Log.d("marker : ", String.valueOf(markers));

        for(int j = 0; j<markers.size(); j++){
            int finalJ = j;

            markers.get(j).setMap(naverMap);
            markers.get(j).setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {

                    infoWindow.open(markers.get(finalJ));

                    //클릭시 정보창 보이기
                    linear_info.setVisibility(View.VISIBLE);
                    btn_exitInfo.setVisibility(View.VISIBLE);

                    //정보 넣어주기
                    tv_infoName.setText(mArrayList.get(finalJ).getBugName());
                    tv_infoAddress.setText(mArrayList.get(finalJ).getAddress());
                    tv_infoDate.setText(mArrayList.get(finalJ).getDate());
                    iv_infoImg.setImageResource(mArrayList.get(finalJ).getBugImg());

                    return true;
                }
            });

        }


        //카메라 줌 설정 : 가장 최근 위치로 설정
        LatLng location = new LatLng(mArrayList.get(mArrayList.size()-1).getLatitude(), mArrayList.get(mArrayList.size()-1).getLongitude());
        CameraPosition cameraPosition = new CameraPosition(location, 14);
        naverMap.setCameraPosition(cameraPosition);


        //정보창
        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence)infoWindow.getMarker().getTag();
            }


        });

    }
}
