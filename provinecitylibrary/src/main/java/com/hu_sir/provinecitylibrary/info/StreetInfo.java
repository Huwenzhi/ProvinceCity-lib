package com.hu_sir.provinecitylibrary.info;

import android.text.TextUtils;

import com.hu_sir.provinecitylibrary.interfaces.MultiItemEntity;

public class StreetInfo implements MultiItemEntity {
    /**
     * code : 110101001
     * name : 东华门街道
     */

    private String code;
    private String name;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getCode() {
        return TextUtils.isEmpty(code) ? "" : code;
    }

    public void setCode(String code) {
        this.code = (TextUtils.isEmpty(code) ? "" : code);
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = (TextUtils.isEmpty(name) ? "" : name);
    }

    @Override
    public int getItemType() {
        return 3;
    }
}
