package com.dish.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-9
 * Time: 下午8:12
 * To change this template use File | Settings | File Templates.
 */
public class PriceUtil {

    public static long getPrice(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        str = str.replaceAll(",", "").replaceAll("元","");
        String[] split = StringUtils.split(str, ".");
        if (split.length == 1) {
            return Long.valueOf(str) * 100;
        }
        if (split.length == 2) {
            String s = split[1];
            if(s.length() == 1){
                s += "0";
            }else if(s.length() >=2){
                s = s.substring(0,2);
            }
            return Long.valueOf(split[0]) * 100 + Long.valueOf(s.substring(0,2));
        }
        return 0;
    }
}
