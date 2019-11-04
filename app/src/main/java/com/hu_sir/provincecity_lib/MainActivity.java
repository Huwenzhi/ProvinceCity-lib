package com.hu_sir.provincecity_lib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hu_sir.provinecitylibrary.ProvinceNewView;
import com.hu_sir.provinecitylibrary.info.AreaInfo;
import com.hu_sir.provinecitylibrary.info.CityInfo;
import com.hu_sir.provinecitylibrary.info.ProvinceInfo;
import com.hu_sir.provinecitylibrary.info.StreetInfo;
import com.hu_sir.provinecitylibrary.interfaces.AdrSelectListener;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    ProvinceNewView provinceNewView;

    private void initView() {

          textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdr(textView);
            }
        });
         provinceNewView=new ProvinceNewView(this, new AdrSelectListener() {
            @Override
            public void onSelcet(ProvinceInfo provinceInfo, CityInfo cityInfo, AreaInfo areaInfo, StreetInfo streetInfo) {

                textView.setText(provinceInfo.getName());


            }
        }).setLayout(ProvinceNewView.HORIZONTAL).setLevel(2).initDialog();

    }

    private void showAdr(final TextView textView) {
      provinceNewView .show();
    }

    public void onDialog() {


    }


}