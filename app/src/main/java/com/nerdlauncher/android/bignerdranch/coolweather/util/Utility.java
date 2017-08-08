package com.nerdlauncher.android.bignerdranch.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nerdlauncher.android.bignerdranch.coolweather.db.City;
import com.nerdlauncher.android.bignerdranch.coolweather.db.Country;
import com.nerdlauncher.android.bignerdranch.coolweather.db.Province;
import com.nerdlauncher.android.bignerdranch.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/8.
 */
public class Utility {
    /**
     * 解析服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String responseJSON){

        if(!TextUtils.isEmpty(responseJSON)){
            try {
                JSONArray allProvince=new JSONArray(responseJSON);
                for(int i=0;i<allProvince.length();i++){
                    JSONObject provinceObject=allProvince.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    /**
     * 解析市级数据,方法中多了个传入数据  provinceId 所属省级的ID
     */
    public static boolean handleCityResponse(String responseJSON, int provinceId){

        if(!TextUtils.isEmpty(responseJSON)){
            try {
                JSONArray allCities=new JSONArray(responseJSON);
                for (int i = 0; i <allCities.length() ; i++) {
                    JSONObject cityObject=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
    /**
     * 县级数据
     */
    public static boolean handleCountryResponse(String responseJSON,int cityId){

        if(!TextUtils.isEmpty(responseJSON)){
            try {
                JSONArray allCounties=new JSONArray(responseJSON);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countryObject=allCounties.getJSONObject(i);
                    Country country=new Country();
                    country.setCountryName(countryObject.getString("name"));
                    country.setWeatherId(countryObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 将返回的JSON解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
