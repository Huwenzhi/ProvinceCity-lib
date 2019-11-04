package com.hu_sir.provinecitylibrary.info;

import android.text.TextUtils;

import com.hu_sir.provinecitylibrary.interfaces.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class AreaInfo implements MultiItemEntity {
    /**
     * code : 110101
     * name : 东城区
     * children : [{"code":"110101001","name":"东华门街道"},{"code":"110101002","name":"景山街道"},{"code":"110101003","name":"交道口街道"},{"code":"110101004","name":"安定门街道"},{"code":"110101005","name":"北新桥街道"},{"code":"110101006","name":"东四街道"},{"code":"110101007","name":"朝阳门街道"},{"code":"110101008","name":"建国门街道"},{"code":"110101009","name":"东直门街道"},{"code":"110101010","name":"和平里街道"},{"code":"110101011","name":"前门街道"},{"code":"110101012","name":"崇文门外街道"},{"code":"110101013","name":"东花市街道"},{"code":"110101014","name":"龙潭街道"},{"code":"110101015","name":"体育馆路街道"},{"code":"110101016","name":"天坛街道"},{"code":"110101017","name":"永定门外街道"}]
     */

    private String code;
    private String name;
    private List<StreetInfo> children;
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

    public List<StreetInfo> getChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<StreetInfo> children) {
        this.children = children;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
