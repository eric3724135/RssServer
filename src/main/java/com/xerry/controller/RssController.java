package com.xerry.controller;

import com.xerry.cache.Cache;
import com.xerry.model.FeedMsg;
import com.xerry.parser.PTTParser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eric on 2016/3/18.
 */
@Controller
public class RssController {
    private static final Logger log = Logger.getLogger(RssController.class);
    @Autowired
    private PTTParser pttParser;

    @RequestMapping(value = "/rss/{board}", method = RequestMethod.GET)
    public ModelAndView getFeedInRss(@PathVariable("board") String board) {
        List<FeedMsg> items = new ArrayList<FeedMsg>();
        try {
            pttParser.getNews(board);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("rssViewer");
        mav.addObject("feedContent", Cache.getPttCache().get(board));

        return mav;
    }
}
