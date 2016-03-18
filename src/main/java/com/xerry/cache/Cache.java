package com.xerry.cache;

import com.xerry.model.FeedMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eric on 2016/3/18.
 */
public class Cache {

    private static Map<String, List<FeedMsg>> pttCache = new HashMap<>();


    public static Map<String, List<FeedMsg>> getPttCache() {
        return pttCache;
    }

}
