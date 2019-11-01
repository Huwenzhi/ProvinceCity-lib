package com.hu_sir.provinecitylibrary.interfaces;

import com.hu_sir.provinecitylibrary.info.AreaInfo;
import com.hu_sir.provinecitylibrary.info.CityInfo;
import com.hu_sir.provinecitylibrary.info.ProvinceInfo;
import com.hu_sir.provinecitylibrary.info.StreetInfo;

public interface AdrSelectListener {
    void onSelcet(ProvinceInfo provinceInfo , CityInfo cityInfo, AreaInfo areaInfo ,StreetInfo streetInfo);
}
