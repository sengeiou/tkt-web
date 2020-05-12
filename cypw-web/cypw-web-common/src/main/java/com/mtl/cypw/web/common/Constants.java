package com.mtl.cypw.web.common;


/**
 * @author tang.
 * @date 2020/2/12.
 */
public class Constants {

    public static String getUserTypeKey(String token) {
        return "user_type_" + token;
    }

    public static String getUserTokenKey(String userType, Integer userId) {
        return "token_" + userType + "_" + userId;
    }

    public static int tokenExpireTime(){
        return 360000;
    }

    public static String getTheatreGeoKey() {
        return "theatre_geo_key" ;
    }
}
