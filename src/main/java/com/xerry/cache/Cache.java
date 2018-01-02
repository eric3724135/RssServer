package com.xerry.cache;

import com.xerry.model.FeedMsg;

import java.util.*;

/**
 * @author  eric on 2016/3/18.
 */
public class Cache {

    private static Set<String> boardCache = new HashSet<>();

    private static Map<String, List<FeedMsg>> pttCache = new HashMap<>();


    public static Map<String, List<FeedMsg>> getPttCache() {
        return pttCache;
    }

    public static void setPttCache(String boead, List<FeedMsg> list) {
        pttCache.put(boead, list);
    }

    public static Set<String> getBoardCache() {
        return boardCache;
    }
}
