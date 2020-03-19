package com.sangjin.buggame;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class EatBugActivity extends AppCompatActivity implements SensorEventListener {

    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float xMax, yMax;
    public Bitmap ball;
    private SensorManager sensorManager;
    private int bugImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //가로,세로 전환 금지

        setContentView(R.layout.activity_eat_bug);



        BallView ballView = new BallView(this);     //뭔지 잘 모르겠지만 ballView라는 것으로 대체한다.
        FrameLayout fl = findViewById(R.id.frameLayout1);
        fl.addView(ballView);

        //화면 크기 받아오기
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 200;
        yMax = (float) size.y - 300;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);   //센서 매니저 초기화
    }


    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }


    //화면이 보이지 않는데 작동할 필요가 없어
    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = sensorEvent.values[0];
            yAccel = -sensorEvent.values[1];
            updateBall();

            //입 안에 들어가면 게임 성공  -> 이 수치를 조절해서 난이도 조절 가능.
            float xSuccess = xMax - 35;
            float ySuccess = yMax - 35;

            //벽에 닿으면 게임 종료
            if(xPos == xMax){
                //벽에 닿았지만 그 범위가 성공 범위 안이라면 성공
                if(yPos > ySuccess){
                    catchBug();
                }
                else{
                    missBug();
                }
            }
            if(yPos == yMax){
                //벽에 닿았지만 그 범위가 성공 범위 안이라면 성공
                if(xPos > xSuccess){
                    catchBug();
                }
                else{
                    missBug();
                }
            }




        }
    }


    private void missBug(){

        sensorManager.unregisterListener(this); //센서 멈추기

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("벌레가 빠져나갔습니다.");
        builder.setMessage("다시 도전하겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //처음 위치로 돌아가고 센서 다시 설치
                        xPos = 0;
                        yPos = 0;
                        xVel = 0;
                        yVel = 0;
                        sensorManager.registerListener(EatBugActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //메인메뉴로 돌아가기
                    }
                });
        builder.show();
    }


    private void catchBug(){
        sensorManager.unregisterListener(this); //센서 멈추기

        //진동 효과 주기
//        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        vibe.vibrate(1000);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("벌레를 먹었습니다.");
        builder.setMessage("잡은 벌레를 저장하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //벌레 등록 액티비티로 이동
                       Intent intent = new Intent(EatBugActivity.this, RegisterBugActivity.class);
                       intent.putExtra("bugImg", bugImg);
                       startActivity(intent);

                       finish();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //메인메뉴로 돌아가기
                        Intent intent = new Intent(EatBugActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                });
        builder.show();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    //볼의 속도 및 위치 최신화
    private void updateBall() {
        float frameTime = 0.666f/4;
        xVel += (xAccel * frameTime);
        yVel += (yAccel * frameTime);

        float xS = (xVel / 2) * frameTime;
        float yS = (yVel / 2) * frameTime;

        xPos -= xS;
        yPos -= yS;

        if (xPos > xMax) {
            xPos = xMax;
        } else if (xPos < 0) {
            xPos = 0;
        }

        if (yPos > yMax) {
            yPos = yMax;
        } else if (yPos < 0) {
            yPos = 0;
        }
    }


    private class BallView extends View {
        public BallView(Context context) {
            super(context);

            Bitmap ballSrc;
            //random값으로 벌레 이미지를 변경해보자.
            int bugRandom= (int)(Math.random()*10);
            if(bugRandom<2){
                ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.img_bug1);
                bugImg = R.drawable.img_bug1;
            }
            else if(bugRandom >= 2 && bugRandom<5){
                ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.img_bug2);
                bugImg = R.drawable.img_bug2;
            }
            else if(bugRandom >= 5 && bugRandom <8){
                ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.img_bug3);
                bugImg = R.drawable.img_bug3;
            }
            else{
                ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.img_bug4);
                bugImg = R.drawable.img_bug4;
            }

            final int dstWidth = 100;
            final int dstHeight = 100;
            ball = Bitmap.createScaledBitmap(ballSrc, dstWidth, dstHeight, true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(ball, xPos, yPos, null);
            invalidate();
        }
    }
}
