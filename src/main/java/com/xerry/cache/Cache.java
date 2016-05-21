package com.xerry.cache;

import com.xerry.model.FeedMsg;
import com.xerry.model.RegInfo;

import java.util.*;

/**
 * Created by eric on 2016/3/18.
 */
public class Cache {

    private static Set<String> broadSet = new HashSet<>();

    private static Map<String, Map<String, RegInfo>> regMap = new HashMap<>();

    private static Map<String, List<FeedMsg>> pttCache = new HashMap<>();





    public static Map<String, List<FeedMsg>> getPttCache() {
        return pttCache;
    }

    public static Set<String> getBroadSet() {
        return broadSet;
    }

    public static Map<String, Map<String, RegInfo>> getRegMap() {
        return regMap;
    }

    public static void setRegMap(Map<String, Map<String, RegInfo>> regMap) {
        Cache.regMap = regMap;
    }
}
