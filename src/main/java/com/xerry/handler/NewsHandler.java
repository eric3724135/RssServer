package com.xerry.handler;

import com.xerry.cache.Cache;
import com.xerry.model.FeedMsg;
import com.xerry.model.RegInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by wwp on 2016/5/21.
 */
public class NewsHandler {
    private static final Logger log = Logger.getLogger(NewsHandler.class);

    private static NewsHandler ourInstance = new NewsHandler();

    public static NewsHandler getInstance() {
        return ourInstance;
    }

    private NewsHandler() {
    }

    public void handleAllNews() {
        for (String board : Cache.getRegMap().keySet()) {
            List<FeedMsg> boardNews = Cache.getPttCache().get(board);
            for (RegInfo info : Cache.getRegMap().get(board).values()) {
                this.handleEachNews(info, boardNews);
                info.setLastUpdateTime(boardNews.get(0).getPubDate());
                Cache.getRegMap().get(board).put(info.getKey(), info);
            }
        }
    }

    private void handleEachNews(RegInfo info, List<FeedMsg> msgs) {
//        for(for)
        for (FeedMsg feedMsg : msgs) {
            if (feedMsg.getPubDate().after(info.getLastUpdateTime())) {
                if (StringUtils.isNotBlank(info.getAuthor())) {
                    if (feedMsg.getAuthor().equals(info.getAuthor())) {
                        MailHandler.getInstance().send(info, feedMsg);
                    }
                } else {
                    if (feedMsg.getTitle().contains(info.getTitle())) {
                        MailHandler.getInstance().send(info, feedMsg);
                    }
                }
            }
        }

    }
}
