package com.hu_sir.provinecitylibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hu_sir.provinecitylibrary.adapter.SelectAdapter;
import com.hu_sir.provinecitylibrary.info.AreaInfo;
import com.hu_sir.provinecitylibrary.info.CityInfo;
import com.hu_sir.provinecitylibrary.info.ProvinceInfo;
import com.hu_sir.provinecitylibrary.info.StreetInfo;
import com.hu_sir.provinecitylibrary.interfaces.AdrSelectListener;
import com.hu_sir.provinecitylibrary.interfaces.MultiItemEntity;
import com.hu_sir.provinecitylibrary.interfaces.OnItemClickListener;
import com.hu_sir.provinecitylibrary.utils.GsonUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProvinceView {
    private static final String TAG = "ProvinceView";
    static Dialog dialog;
    static RecyclerView myRecycler;
    static View view;
    static SelectAdapter selectAdapter;
    static List<ProvinceInfo> states;
    static List<MultiItemEntity> multiItemEntityList = new ArrayList<>();
    static List<MultiItemEntity> multiItemEntityListcopry = new ArrayList<>();
    static LinearLayout proLayout, cityLayout, areaLayout, streetLayout;
    static TextView proName, cityName, areaName, streetName;
    static Activity mcontext;

    static ProvinceInfo provinceInfo;
    static  CityInfo cityInfo;
    static   AreaInfo areaInfo;
    static  StreetInfo streetInfo;

    public ProvinceView(Activity mcontext) {
        this.mcontext = mcontext;
//        initView();
//        initDialog();
    }

    public static void initDialog(Activity contenxt) {
        mcontext = contenxt;
        dialog = new Dialog(mcontext);
        initView();
        view.setMinimumWidth(10000);
        //可以在style中设定dialog的样式
        dialog.setContentView(view);
        Display display = mcontext.getWindowManager().getDefaultDisplay();
        int weight = display.getWidth();
        int height = display.getHeight();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = height * 2 / 3;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setDimAmount(0f);
        //设置该属性，dialog可以铺满屏幕
        dialog.getWindow().setBackgroundDrawable(null);

        initadapter(mcontext);
        dialog.show();
        slideToUp(dialog.getWindow().findViewById(R.id.layout));

    }

    public boolean isDialog() {
        return false;
    }


    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            ((ViewGroup) view.getParent()).removeView(view);

        }
    }

    public static void initView() {

        view = View.inflate(mcontext, R.layout.pop_view, null);
        myRecycler = view.findViewById(R.id.my_recy);
        proLayout = view.findViewById(R.id.layout_pro);
        cityLayout = view.findViewById(R.id.layout_city);
        areaLayout = view.findViewById(R.id.layout_area);
        streetLayout = view.findViewById(R.id.layout_street);

        proName = view.findViewById(R.id.pro_name);
        cityName = view.findViewById(R.id.city_name);
        areaName = view.findViewById(R.id.area_name);
        streetName = view.findViewById(R.id.street_name);


    }

    /**
     * 初始化 adapter
     *
     * @param context
     */
    private static void initadapter(Activity context) {
        List<ProvinceInfo> list = getStates(context);
        for (ProvinceInfo provinceInfo : list) {
            multiItemEntityList.add(provinceInfo);
            multiItemEntityListcopry.add(provinceInfo);
        }
        selectAdapter = new SelectAdapter(context, multiItemEntityList);
        myRecycler.setLayoutManager(new LinearLayoutManager(context));
        myRecycler.setAdapter(selectAdapter);
        initEvent();

    }

    private static void initEvent() {
        proLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                getProines();
                proName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                selectAdapter.notifyDataSetChanged();
            }
        });
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                cityName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                getCities();
                selectAdapter.notifyDataSetChanged();
            }
        });

        areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                areaName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                getAreaInfo();
                selectAdapter.notifyDataSetChanged();

            }
        });

        streetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                streetName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                getStreets();
                selectAdapter.notifyDataSetChanged();
            }
        });


        selectAdapter.setOnItemClickListerner(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
                MultiItemEntity multiItemEntity = multiItemEntityList.get(position);
                changeList(multiItemEntity);

                adapter.notifyDataSetChanged();

            }
        });

    }

    private static void defaultTextColor() {
        proName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
        cityName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
        areaName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
        streetName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
    }

    private static void getProines() {
        multiItemEntityList.clear();
        multiItemEntityList.addAll(multiItemEntityListcopry);
    }


    private static void changeList(MultiItemEntity multiItemEntity) {
        defaultTextColor();
        if (multiItemEntity.getItemType() == 0) {
            provinceInfo = (ProvinceInfo) multiItemEntity;
            proName.setText(provinceInfo.getName());
            cityLayout.setVisibility(View.VISIBLE);
            cityName.setText("请选择城市");
            cityName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            areaLayout.setVisibility(View.GONE);
            streetLayout.setVisibility(View.GONE);
            getCities();
        } else if (multiItemEntity.getItemType() == 1) {
            cityInfo = (CityInfo) multiItemEntity;
            cityName.setText(cityInfo.getName());
            areaLayout.setVisibility(View.VISIBLE);
            areaName.setText("请选择区县");
            areaName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            streetLayout.setVisibility(View.GONE);
            getAreaInfo();
        } else if (multiItemEntity.getItemType() == 2) {
            areaInfo = (AreaInfo) multiItemEntity;
            areaName.setText(areaInfo.getName());
            streetLayout.setVisibility(View.VISIBLE);
            streetName.setText("请选择乡镇或街道");
            streetName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            getStreets();
        } else if (multiItemEntity.getItemType() == 3) {
            streetName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            streetInfo = (StreetInfo) multiItemEntity;
            streetName.setText(streetInfo.getName());
        }


    }

    private static void getStreets() {
        List<StreetInfo> streetInfos = areaInfo.getChildren();
        multiItemEntityList.clear();
        for (StreetInfo streetInfo : streetInfos) {
            multiItemEntityList.add(streetInfo);
        }
    }

    private static void getAreaInfo() {
        List<AreaInfo> areaInfos = cityInfo.getChildren();
        multiItemEntityList.clear();
        for (AreaInfo areaInfo : areaInfos) {
            multiItemEntityList.add(areaInfo);
        }
    }

    private static void getCities() {
        List<CityInfo> cityInfos = provinceInfo.getChildren();
        multiItemEntityList.clear();
        for (CityInfo cityInfo : cityInfos) {
            multiItemEntityList.add(cityInfo);
        }
    }


    public static List<ProvinceInfo> getStates(Context context) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = context.getAssets().open("pcas-code.json");
            bos = new ByteArrayOutputStream();
            byte[] bytes = new byte[4 * 1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            final String json = new String(bos.toByteArray());

            states = GsonUtil.jsonToBeanList2(json, ProvinceInfo.class);


            return states;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                Log.e(TAG, "getStates", e);
            }
        }
        return null;
    }

    public static void slideToUp(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(2000);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
