package com.sangjin.buggame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class GetBugActivity extends AppCompatActivity implements SensorEventListener {

    ImageView iv_getBug;
    TextView tv_count;
    ConstraintLayout layout_getBug;
    private SensorManager sensorManager;
    private Sensor countSensor;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bug);

        iv_getBug = findViewById(R.id.iv_getBug);
        tv_count = findViewById(R.id.tv_count);
        layout_getBug = findViewById(R.id.layout_getBug);

        //벌레 애니메이션
        Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        iv_getBug.startAnimation(animRotate);

        //흔들기 센서
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            count += (int) event.values[0];

            //카운트 세주기
            tv_count.setText("카운트 : " + count);

            //카운트에 따라 배경색 바꿔주기
            int colorPercent = count * 10;
            layout_getBug.setBackgroundColor(Color.argb(colorPercent, 255, 135, 135));

            //카운트가 10이 되면 센서 종료 및 알람창 띄워주기
            if(count == 10){
                sensorManager.unregisterListener(this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("벌레를 잡았습니다.");
                builder.setMessage("잡은 벌레를 캐릭터에게 먹이겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //벌레 등록 액티비티로 이동
                                Intent intent = new Intent(GetBugActivity.this, EatBugActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //메인메뉴로 돌아가기
                                Intent intent = new Intent(GetBugActivity.this, MainActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        });
                builder.show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
