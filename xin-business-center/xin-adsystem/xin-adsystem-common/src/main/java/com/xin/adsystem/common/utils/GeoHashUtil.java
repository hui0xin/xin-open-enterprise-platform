package com.xin.adsystem.common.utils;

import ch.hsr.geohash.GeoHash;

/**
 * @author xin
 * @date 2019/11/18
 */
public class GeoHashUtil {
    /**
     * 赤道半径(单位m)
     */
    private static final double R = 6378137;

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
     *
     * @param lat1 第一点的精度
     * @param lng1 第一点的纬度
     * @param lat2 第二点的精度
     * @param lng2 第二点的纬度
     * @return 返回的距离，单位m
     */

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double x1 = Math.cos(lat1) * Math.cos(lng1);
        double y1 = Math.cos(lat1) * Math.sin(lng1);
        double z1 = Math.sin(lat1);
        double x2 = Math.cos(lat2) * Math.cos(lng2);
        double y2 = Math.cos(lat2) * Math.sin(lng2);
        double z2 = Math.sin(lat2);
        double lineDistance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        double s = R * Math.PI * 2 * Math.asin(0.5 * lineDistance) / 180;
        return Math.round(s * 10000) / 10000;
    }

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
     *
     * @param lat1 第一点的精度
     * @param lng1 第一点的纬度
     * @param lat2 第二点的精度
     * @param lng2 第二点的纬度
     * @return 返回的距离，单位m
     */

    public static Long getDistances(double lat1, double lng1, double lat2, double lng2) {
        double x1 = Math.cos(lat1) * Math.cos(lng1);
        double y1 = Math.cos(lat1) * Math.sin(lng1);
        double z1 = Math.sin(lat1);
        double x2 = Math.cos(lat2) * Math.cos(lng2);
        double y2 = Math.cos(lat2) * Math.sin(lng2);
        double z2 = Math.sin(lat2);
        double lineDistance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        double s = R * Math.PI * 2 * Math.asin(0.5 * lineDistance) / 180;
        return Math.round(s * 10000) / 10000;
    }

    /**
     * 获取经纬度
     *
     * @param lng 116.724331 经度
     * @return lat 40.19099  纬度
     * * geohash length       width            height
     * * 第1位                 5009.4km         4992.6km
     * * 第2位                 1252.3km         624.1km
     * * 第3位                 156.5km          156km
     * * 第4位                 39.1km           19.5km
     * * 第5位                 4.9km            4.9km
     * * 第6位                 1.2km            609.4m
     * * 第7位                 152.9m           152.4m
     * * 第8位                 38.2m            19m
     * * 第9位                 4.8m             4.8m
     */
    public static String getGeoHash(double lng, double lat) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lng, 12);
        return geoHash.toBase32();
    }

    /**
     * 通过查询的距离查询 截取geoHash
     *
     * @return distances 查询距离 单位是m
     * * geohash length       width            height
     * * 第1位                 5009.4km         4992.6km
     * * 第2位                 1252.3km         624.1km
     * * 第3位                 156.5km          156km
     * * 第4位                 39.1km           19.5km
     * * 第5位                 4.9km            4.9km
     * * 第6位                 1.2km            609.4m
     * * 第7位                 152.9m           152.4m
     * * 第8位                 38.2m            19m
     * * 第9位                 4.8m             4.8m
     */
    public static String getQueryGeoHash(String geoHashStr, double distances) {

        if (distances > 624.1 * 1000) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 1);
        }
        if (distances <= 624.1 * 1000 && distances > 156 * 1000) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 2);
        }
        if (distances <= 156 * 1000 && distances > 19.5 * 1000) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 3);
        }
        if (distances <= 19.5 * 1000 && distances > 4.9 * 1000) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 4);
        }
        if (distances <= 4.9 * 1000 && distances > 609.4) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 5);
        }
        if (distances <= 609.4 && distances > 152.4) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 6);
        }
        if (distances <= 152.4 && distances > 19) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 7);
        }
        if (distances <= 19 && distances > 4.8) {
            //截取一位
            geoHashStr = geoHashStr.substring(0, 8);
        }
        return geoHashStr;
    }

    public static void main(String[] args) {
        double lng = 113.6738204956;
        double lat = 34.8090113539;
        String geoHash = getGeoHash(lng, lat);
        System.out.println(geoHash);
        System.out.println(getQueryGeoHash(geoHash, 4.5 * 1000));


    }
}