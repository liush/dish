package com.dish.util;

import com.dish.base.DishConst;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-2
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
public class SpellUtil {
    //  国标码和区位码转换常量
    private static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    private static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600};
    // 存放国标一级汉字不同读音的起始区位码对应读音
    private static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
            'y', 'z'};

    // 获取一个字符串的拼音码
    public static String getFirstLetter(String oriStr) {
        StringBuilder builder = new StringBuilder();
        for (char c : oriStr.toCharArray()) { // 依次处理str中每个字符
            byte[] uniCode;
            try {
                uniCode = String.valueOf(c).getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
                builder.append(c);
            } else {
                builder.append(convert(uniCode));
            }
        }
        return builder.toString();
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    private static char convert(byte[] bytes) {
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        int secPosValue = bytes[0] * 100 + bytes[1];
        char result = '-';
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            byte[] digest = instance.digest("qqq000".getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                String s = Integer.toHexString(b & 0xFF);
                if (s.length() == 1) {
                    builder.append(DishConst.ZERO_STR);
                }
                builder.append(s);
            }
            System.out.println(builder.toString()) ;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
