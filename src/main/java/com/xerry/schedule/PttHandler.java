package com.xerry.schedule;

import com.xerry.cache.Cache;
import com.xerry.parser.PTTParser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.TimerTask;

/**
 * Created by wwp on 2016/5/21.
 */
public class PttHandler extends TimerTask {
    private static final Logger log = Logger.getLogger(PTTParser.class);

    @Autowired
    private PTTParser pttParser;

    @Override
    public void run() {
        for (String board : Cache.getBroadSet()) {
            try {
                pttParser.getNews(board);
            } catch (Exception e) {
                log.error("[PttHandler] error" + ExceptionUtils.getStackTrace(e));
            }
        }


    }
}
