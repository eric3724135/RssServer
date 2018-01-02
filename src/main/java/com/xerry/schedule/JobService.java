package com.xerry.schedule;

import com.xerry.cache.Cache;
import com.xerry.parser.PTTParser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

/**
 * @author  eric on 2016/3/30.
 */
@Service
public class JobService {
    @Autowired
    private PTTParser pttParser;

    @Scheduled(fixedDelay = 20000)
    public void scheduleParsePTT() {
        Iterator<String> boards = Cache.getBoardCache().iterator();
        while (boards.hasNext()) {
            String board = boards.next();
            try {
                pttParser.getNews(board);
            } catch (Exception e) {
                ExceptionUtils.getStackTrace(e);
            }

        }
    }
}
