package com.yxdtyut.utils;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:14 2018/7/23
 */

public class StringUtil {
    public static List<Integer> parseStringToIntList(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
    }
}
