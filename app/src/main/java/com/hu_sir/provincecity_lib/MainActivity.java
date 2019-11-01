package com.hu_sir.provincecity_lib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.hu_sir.provinecitylibrary.DialogActivity;
import com.hu_sir.provinecitylibrary.ProvinceView;
import com.hu_sir.provinecitylibrary.info.AreaInfo;
import com.hu_sir.provinecitylibrary.info.CityInfo;
import com.hu_sir.provinecitylibrary.info.ProvinceInfo;
import com.hu_sir.provinecitylibrary.info.StreetInfo;
import com.hu_sir.provinecitylibrary.interfaces.AdrSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        final TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdr(textView);
            }
        });


//    startActivity(new Intent(this,DialogActivity.class));
//    onDialog();
    }

    private void showAdr(final TextView textView) {
        ProvinceView provinceView = new ProvinceView(this);
        provinceView.initView();
        provinceView.setAdrSelectListener(new AdrSelectListener() {
            @Override
            public void onSelcet(ProvinceInfo provinceInfo, CityInfo cityInfo, AreaInfo areaInfo, StreetInfo streetInfo) {
                textView.setText(provinceInfo.getName() + cityInfo.getName() + areaInfo.getName() + streetInfo.getName());

            }
        }).show();
    }

    public void onDialog() {


    }


}