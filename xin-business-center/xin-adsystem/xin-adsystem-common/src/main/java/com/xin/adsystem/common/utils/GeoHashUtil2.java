package com.xin.adsystem.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xin
 * @date 2019/11/18
 *
 * geohash length       width            height
 * 第1位                 5009.4km         4992.6km
 * 第2位                 1252.3km         624.1km
 * 第3位                 156.5km          156km
 * 第4位                 39.1km           19.5km
 * 第5位                 4.9km            4.9km
 * 第6位                 1.2km            609.4m
 * 第7位                 152.9m           152.4m
 * 第8位                 38.2m            19m
 * 第9位                 4.8m             4.8m
 */
public class GeoHashUtil2 {
    /**
     * 赤道半径(单位m)
     */
    private static final  double EARTH_RADIUS = 6378137;

    private LocationBean location;

    /**
     * 经纬度转化为geohash长度
     */
    private int hashLength = 8;
    /**
     * 纬度转化为二进制长度
     */
    private int latLength = 20;
    /**
     * 经度转化为二进制长度
     */
    private int lngLength = 20;
    /**
     * 每格 纬度的单位大小
     */
    private double minLat;
    /**
     * 每格 经度的单位大小
     */
    private double minLng;

    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public GeoHashUtil2(double lat, double lng) {
        location = new LocationBean(lat, lng);
        setMinLatLng();
    }

    public int gethashLength() {
        return hashLength;
    }

    /**
     * 设置经纬度的最小单位
     */
    private void setMinLatLng() {
        minLat = LocationBean.MAXLAT - LocationBean.MINLAT;
        for (int i = 0; i < latLength; i++) {
            minLat /= 2.0;
        }
        minLng = LocationBean.MAXLNG - LocationBean.MINLNG;
        for (int i = 0; i < lngLength; i++) {
            minLng /= 2.0;
        }
    }

    /**
     * 求所在坐标点及周围点组成的九个
     */
    public List<String> getGeoHashBase32For9() {
        double leftLat = location.getLat() - minLat;
        double rightLat = location.getLat() + minLat;
        double upLng = location.getLng() - minLng;
        double downLng = location.getLng() + minLng;
        List<String> base32For9 = new ArrayList<String>();
        //左侧从上到下 3个
        String leftUp = getGeoHashBase32(leftLat, upLng);
        if (!(leftUp == null || "".equals(leftUp))) {
            base32For9.add(leftUp);
        }
        String leftMid = getGeoHashBase32(leftLat, location.getLng());
        if (!(leftMid == null || "".equals(leftMid))) {
            base32For9.add(leftMid);
        }
        String leftDown = getGeoHashBase32(leftLat, downLng);
        if (!(leftDown == null || "".equals(leftDown))) {
            base32For9.add(leftDown);
        }
        //中间从上到下 3个
        String midUp = getGeoHashBase32(location.getLat(), upLng);
        if (!(midUp == null || "".equals(midUp))) {
            base32For9.add(midUp);
        }
        String midMid = getGeoHashBase32(location.getLat(), location.getLng());
        if (!(midMid == null || "".equals(midMid))) {
            base32For9.add(midMid);
        }
        String midDown = getGeoHashBase32(location.getLat(), downLng);
        if (!(midDown == null || "".equals(midDown))) {
            base32For9.add(midDown);
        }
        //右侧从上到下 3个
        String rightUp = getGeoHashBase32(rightLat, upLng);
        if (!(rightUp == null || "".equals(rightUp))) {
            base32For9.add(rightUp);
        }
        String rightMid = getGeoHashBase32(rightLat, location.getLng());
        if (!(rightMid == null || "".equals(rightMid))) {
            base32For9.add(rightMid);
        }
        String rightDown = getGeoHashBase32(rightLat, downLng);
        if (!(rightDown == null || "".equals(rightDown))) {
            base32For9.add(rightDown);
        }
        return base32For9;
    }

    /**
     * 设置经纬度转化为geohash长度
     */
    public boolean sethashLength(int length) {
        if (length < 1) {
            return false;
        }
        hashLength = length;
        latLength = (length * 5) / 2;
        if (length % 2 == 0) {
            lngLength = latLength;
        } else {
            lngLength = latLength + 1;
        }
        setMinLatLng();
        return true;
    }

    /**
     * 获取经纬度的base32字符串
     */
    public String getGeoHashBase32() {
        return getGeoHashBase32(location.getLat(), location.getLng());
    }

    /**
     * 获取经纬度的base32字符串
     */
    private String getGeoHashBase32(double lat, double lng) {
        boolean[] bools = getGeoBinary(lat, lng);
        if (bools == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bools.length; i = i + 5) {
            boolean[] base32 = new boolean[5];
            for (int j = 0; j < 5; j++) {
                base32[j] = bools[i + j];
            }
            char cha = getBase32Char(base32);
            if (' ' == cha) {
                return null;
            }
            sb.append(cha);
        }
        return sb.toString();
    }

    /**
     * 将五位二进制转化为base32
     */
    private char getBase32Char(boolean[] base32) {
        if (base32 == null || base32.length != 5) {
            return ' ';
        }
        int num = 0;
        for (boolean bool : base32) {
            num <<= 1;
            if (bool) {
                num += 1;
            }
        }
        return CHARS[num % CHARS.length];
    }

    /**
     * 获取坐标的geo二进制字符串
     */
    private boolean[] getGeoBinary(double lat, double lng) {
        boolean[] latArray = getHashArray(lat, LocationBean.MINLAT, LocationBean.MAXLAT, latLength);
        boolean[] lngArray = getHashArray(lng, LocationBean.MINLNG, LocationBean.MAXLNG, lngLength);
        return merge(latArray, lngArray);
    }

    /**
     * 合并经纬度二进制
     */
    private boolean[] merge(boolean[] latArray, boolean[] lngArray) {
        if (latArray == null || lngArray == null) {
            return null;
        }
        boolean[] result = new boolean[lngArray.length + latArray.length];
        Arrays.fill(result, false);
        for (int i = 0; i < lngArray.length; i++) {
            result[2 * i] = lngArray[i];
        }
        for (int i = 0; i < latArray.length; i++) {
            result[2 * i + 1] = latArray[i];
        }
        return result;
    }

    /**
     * 将数字转化为geohash二进制字符串
     */
    private boolean[] getHashArray(double value, double min, double max, int length) {
        if (value < min || value > max) {
            return null;
        }
        if (length < 1) {
            return null;
        }
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            double mid = (min + max) / 2.0;
            if (value > mid) {
                result[i] = true;
                min = mid;
            } else {
                result[i] = false;
                max = mid;
            }
        }
        return result;
    }

    class LocationBean {
        public static final double MINLAT = -90;
        public static final double MAXLAT = 90;
        public static final double MINLNG = -180;
        public static final double MAXLNG = 180;
        private double lat;//纬度[-90,90]
        private double lng;//经度[-180,180]

        public LocationBean(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
        public double getLat() {
            return lat;
        }
        public void setLat(double lat) {
            this.lat = lat;
        }
        public double getLng() {
            return lng;
        }
        public void setLng(double lng) {
            this.lng = lng;
        }
    }


    /**
     * @param lat1 起始地纬度
     * @param lon1 起始地经度
     * @param lat2 目的地纬度
     * @param lon2 目的地经度
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        if (Math.abs(lat1) > 90 || Math.abs(lon1) > 180
                || Math.abs(lat2) > 90 || Math.abs(lon2) > 180) {
            throw new IllegalArgumentException("The supplied coordinates are out of range.");
        }
        //赤道直径
        double latRad1 = Math.toRadians(lat1);
        double lonRad1 = Math.toRadians(lon1);
        double latRad2 = Math.toRadians(lat2);
        double lonRad2 = Math.toRadians(lon2);
        //结果
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((latRad1 - latRad2) / 2), 2)
                + Math.cos(latRad1) * Math.cos(latRad2) * Math.pow(Math.sin((lonRad1 - lonRad2) / 2), 2))) * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        return Math.round(distance);
    }

    public static void main(String[] args) {

    }
}