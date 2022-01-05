package com.cumt.bigdata;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

/**
 * @Author: ahui
 * @date: 2021/12/25 - 21:13
 */
public class EntCoordSyncJob {
    static String AK = "Xk86X9MevzfvRR3iDnUySDeoGGAPSPEU"; // 百度地图密钥

    public static void main(String[] args) {
        String dom = "深圳市";
        String coordinate = getCoordinate(dom);
        System.out.println("'" + dom + "'的经纬度为：" + coordinate);
        // System.err.println("######同步坐标已达到日配额6000限制，请明天再试！#####");
    }

    // 调用百度地图API根据地址，获取坐标
    public static String getCoordinate(String address) {
        if (address.equals("松山湖南")){
            address+="站";
        } else if (address.equals("广东富安站")) {
            address = "广东富安收费站";
        } else if (address.equals("广东清湖站")){
            address = "广东清湖收费站";
        } else if (address.equals("广东灵山站")){
            address = "广东省广州市南沙区灵山收费站";
        } else if (address.equals("广东莞樟路站")){
            address = "广东莞樟路收费站";
        } else if (address.equals("广东罗田主线站")){
            address = "广东省深圳市宝安区罗田收费站";
        } else if (address.equals("广东龙岗站水官")) {
            address = "广东龙岗收费站(S28水官高速出口)";
        } else if (address.equals("广东石大路站")){
            address = "广东石大路收费站";
        } else if (address.equals("广东福民站")) {
            address = "广东福民收费站";
        } else if (address.equals("广东清溪站")) {
            address = "广东清溪收费站";
        } else if (address.equals("广东太平站广深站")) {
            address = "广东省东莞市赤岭东莞收费站(广深高速公路东)";
        } else if (address.equals("广东佛高区乐平收费站")) {
            address = "";
        }

        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = "http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=" + AK;
            String json = loadJSON(url);
            if (json != null && !"".equals(json)) {
                JSONObject obj = JSONObject.fromObject(json);
                if ("0".equals(obj.getString("status"))) {
                    double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
                    double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
                    DecimalFormat df = new DecimalFormat("#.######");
                    return df.format(lng) + "," + df.format(lat);
                }
            }
        }
        return null;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    /*
     * public String MD5(String md5) { try { java.security.MessageDigest md = java.security.MessageDigest .getInstance("MD5"); byte[] array = md.digest(md5.getBytes()); StringBuffer sb = new StringBuffer(); for (int i = 0; i < array.length; ++i) { sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100) .substring(1, 3)); } return sb.toString(); } catch (java.security.NoSuchAlgorithmException e) {
     * } return null; }
     */
}