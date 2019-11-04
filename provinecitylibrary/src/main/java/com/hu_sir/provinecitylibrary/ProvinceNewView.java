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
import android.widget.ImageView;
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

public class ProvinceNewView {
    private static final String TAG = "ProvinceView";
    private Dialog dialog;
    private RecyclerView myRecycler;
    private View view;
    private SelectAdapter selectAdapter;
    private List<ProvinceInfo> states;
    private List<MultiItemEntity> multiItemEntityList = new ArrayList<>();
    private List<MultiItemEntity> multiItemEntityListcopry = new ArrayList<>();
    private LinearLayout proLayout, cityLayout, areaLayout, streetLayout;
    private ImageView proImage, cityImage, areaImage, streetImage;
    private TextView proName, cityName, areaName, streetName;
    private Activity mcontext;

    private ProvinceInfo provinceInfo;
    private CityInfo cityInfo;
    private AreaInfo areaInfo;
    private StreetInfo streetInfo;
    public static int VERTICAL = 0, HORIZONTAL = 1;
    private int laoutId;
    private AdrSelectListener adrSelectListener;
    int level = 3;
    public static int PROVINE_LEVEL = 0;
    public static int CITY_LEVEL = 1;
    public static int AREA_LEVEL = 2;
    public static int STREET_LEVEL = 3;


    public ProvinceNewView(Activity mcontext, AdrSelectListener adrSelectListener) {
        this.mcontext = mcontext;
        this.adrSelectListener = adrSelectListener;
    }

    public ProvinceNewView(Activity mcontext) {
        this.mcontext = mcontext;
    }

    public ProvinceNewView initDialog() {
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
        initadapter();
        slideToUp(dialog.getWindow().findViewById(R.id.layout));
        return this;

    }

    boolean needremove = false;

    public ProvinceNewView showwithremove() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
        needremove = true;
        return this;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

    }


    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            if (needremove)
                ((ViewGroup) view.getParent()).removeView(view);

        }
    }

    public void initView() {

        view = View.inflate(mcontext, laoutId == VERTICAL ? R.layout.view_v : R.layout.view_h, null);
        myRecycler = view.findViewById(R.id.my_recy);
        proLayout = view.findViewById(R.id.layout_pro);
        cityLayout = view.findViewById(R.id.layout_city);
        areaLayout = view.findViewById(R.id.layout_area);
        streetLayout = view.findViewById(R.id.layout_street);

        proImage = view.findViewById(R.id.pro_imaege);
        cityImage = view.findViewById(R.id.city_image);
        areaImage = view.findViewById(R.id.area_image);
        streetImage = view.findViewById(R.id.street_image);

        proName = view.findViewById(R.id.pro_name);
        cityName = view.findViewById(R.id.city_name);
        areaName = view.findViewById(R.id.area_name);
        streetName = view.findViewById(R.id.street_name);


    }

    /**
     * 初始化 adapter
     *
     * @param
     */
    private void initadapter() {
        if (multiItemEntityListcopry.size() == 0) {

            List<ProvinceInfo> list = getStates(mcontext);
            for (ProvinceInfo provinceInfo : list) {
                multiItemEntityList.add(provinceInfo);
                multiItemEntityListcopry.add(provinceInfo);
            }
        } else {
            multiItemEntityList.clear();
            multiItemEntityList.addAll(multiItemEntityListcopry);
        }
        if (selectAdapter == null) {

            selectAdapter = new SelectAdapter(mcontext, multiItemEntityList);
        }
        myRecycler.setLayoutManager(new LinearLayoutManager(mcontext));
        myRecycler.setAdapter(selectAdapter);
        initEvent();

    }

    private void initEvent() {
        proLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                defaultImage();
                getProines();
                selectAdapter.setSelectProvine(provinceInfo);
                proName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                proImage.setImageResource(R.drawable.solid);
                selectAdapter.notifyDataSetChanged();
            }
        });
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                defaultImage();
                cityName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                cityImage.setImageResource(R.drawable.solid);
                getCities();
                selectAdapter.notifyDataSetChanged();
            }
        });

        areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                defaultImage();
                areaName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                areaImage.setImageResource(R.drawable.solid);
                getAreaInfo();
                selectAdapter.notifyDataSetChanged();

            }
        });

        streetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTextColor();
                defaultImage();
                streetName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
                streetImage.setImageResource(R.drawable.solid);
                getStreets();
                selectAdapter.notifyDataSetChanged();
            }
        });


        selectAdapter.setOnItemClickListerner(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
                MultiItemEntity multiItemEntity = multiItemEntityList.get(position);
                //判断设置的级别
                if (multiItemEntity.getItemType() <= level) {
                    changeList(multiItemEntity);
                    adapter.notifyDataSetChanged();
                    if (multiItemEntity.getItemType() == level) {
                        dismissDialog();
                        adrSelectListener.onSelcet(provinceInfo, cityInfo, areaInfo, streetInfo);
                    }
                }
            }
        });

    }

    public ProvinceNewView setLayout(int laoutId) {
        if (laoutId == 0 || laoutId == 1)
            this.laoutId = laoutId;
        return this;
    }

    public ProvinceNewView setLevel(int level) {
        if (level >= 0 && level <= 3) {
            this.level = level;
        }

        return this;
    }

    private void defaultTextColor() {
        proName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
        cityName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
        areaName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
        streetName.setTextColor(mcontext.getResources().getColor(R.color.md_black_1000));
    }

    private void getProines() {
        multiItemEntityList.clear();
        multiItemEntityList.addAll(multiItemEntityListcopry);
    }


    private void changeList(MultiItemEntity multiItemEntity) {
        defaultTextColor();
        defaultImage();
        if (multiItemEntity.getItemType() == 0) {
            provinceInfo = (ProvinceInfo) multiItemEntity;
            proName.setText(provinceInfo.getName());
            cityLayout.setVisibility(View.VISIBLE);
            cityName.setText("请选择城市");
            cityImage.setImageResource(R.drawable.solid);
            cityName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            areaLayout.setVisibility(View.GONE);
            streetLayout.setVisibility(View.GONE);

            getCities();
        } else if (multiItemEntity.getItemType() == 1) {
            cityInfo = (CityInfo) multiItemEntity;
            cityName.setText(cityInfo.getName());
            areaLayout.setVisibility(View.VISIBLE);
            areaImage.setImageResource(R.drawable.solid);
            areaName.setText("请选择区县");
            areaName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            streetLayout.setVisibility(View.GONE);
            getAreaInfo();
        } else if (multiItemEntity.getItemType() == 2) {
            streetImage.setImageResource(R.drawable.solid);
            areaInfo = (AreaInfo) multiItemEntity;
            areaName.setText(areaInfo.getName());
            streetLayout.setVisibility(View.VISIBLE);
            streetName.setText("请选择乡镇或街道");
            streetName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            getStreets();
        } else if (multiItemEntity.getItemType() == 3) {
            streetImage.setImageResource(R.drawable.solid);
            streetName.setTextColor(mcontext.getResources().getColor(R.color.md_red_600));
            streetInfo = (StreetInfo) multiItemEntity;
            streetName.setText(streetInfo.getName());

        }


    }

    private void defaultImage() {
        proImage.setImageResource(R.drawable.hollow);
        cityImage.setImageResource(R.drawable.hollow);
        areaImage.setImageResource(R.drawable.hollow);
        streetImage.setImageResource(R.drawable.hollow);
    }

    private void getStreets() {
        List<StreetInfo> streetInfos = areaInfo.getChildren();
        multiItemEntityList.clear();
        for (StreetInfo streetInfo : streetInfos) {
            multiItemEntityList.add(streetInfo);
            streetInfo.setChecked(this.streetInfo != null ? (this.streetInfo.getCode() == streetInfo.getCode()) : false);
        }
    }

    private void getAreaInfo() {
        List<AreaInfo> areaInfos = cityInfo.getChildren();
        multiItemEntityList.clear();
        for (AreaInfo areaInfo : areaInfos) {
            multiItemEntityList.add(areaInfo);
            areaInfo.setChecked(this.areaInfo != null ? (this.areaInfo.getCode() == areaInfo.getCode()) : false);
        }
    }

    private void getCities() {
        List<CityInfo> cityInfos = provinceInfo.getChildren();
        multiItemEntityList.clear();
        for (CityInfo cityInfo : cityInfos) {
            multiItemEntityList.add(cityInfo);
            cityInfo.setChecked(this.cityInfo != null ? (this.cityInfo.getCode() == cityInfo.getCode()) : false);
        }
    }


    private List<ProvinceInfo> getStates(Context context) {
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

    /**
     * 加载动画
     *
     * @param view
     */
    public void slideToUp(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(1000);
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
