package com.sangjin.buggame.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sangjin.buggame.R;

public class GetBugViewModel extends ViewModel {

    public int bugImg;
    public MutableLiveData<Integer> count;

    public GetBugViewModel() {
        super();
        selectBug();
        count = new MutableLiveData<Integer>();
        count.setValue(0);
    }

    public MutableLiveData<Integer> getLiveData(){
        if(count == null){
            count = new MutableLiveData<Integer>();
        }
        return count;
    }


    //게임 시작시 랜덤으로 벌레 선택
    private int selectBug(){
        //랜덤 값에 따라 벌레 이미지 결정
        int bugRandom= (int)(Math.random()*10);
        if(bugRandom<2){
            bugImg = R.drawable.img_bug1;
        }
        else if(bugRandom >= 2 && bugRandom<5){
            bugImg = R.drawable.img_bug2;
        }
        else if(bugRandom >= 5 && bugRandom <8){
            bugImg = R.drawable.img_bug3;
        }
        else{
            bugImg = R.drawable.img_bug4;
        }

        return bugImg;
    }


    //걸음수가 인식되면 count 올리기
    public void countUp(){
        count.setValue(count.getValue() + 1);
    }


}
