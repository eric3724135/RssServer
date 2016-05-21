package com.xerry.controller;

import com.xerry.cache.Cache;
import com.xerry.model.FeedMsg;
import com.xerry.model.RegInfo;
import com.xerry.parser.PTTParser;
import com.xerry.schedule.TimerInitial;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by eric on 2016/3/18.
 */
@Controller
public class RssController {
    private static final Logger log = Logger.getLogger(RssController.class);
//    @Autowired
//    private PTTParser pttParser;
//
//    @RequestMapping(value = "/rss/ptt/{board}", method = RequestMethod.GET)
//    public ModelAndView getFeedInRss(@PathVariable("board") String board) {
//        log.info("access : /rss/ptt/" + board + "Time:" + new Date());
//        List<FeedMsg> items = new ArrayList<FeedMsg>();
//        try {
//            pttParser.getNews(board);
//        } catch (Exception e) {
//            log.error(ExceptionUtils.getStackTrace(e));
//        }
//
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("rssViewer");
//        mav.addObject("feedContent", Cache.getPttCache().get(board));
//
//        return mav;
//    }

    @RequestMapping(value = "/notify/ptt/{board}", method = RequestMethod.GET)
    @ResponseBody
    public void register(@PathVariable("board") String board, @RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("mail") String mail) {
        if (StringUtils.isBlank(board)) {
            throw new NullPointerException("unknown board ");
        }
        if (StringUtils.isBlank(mail)) {
            throw new NullPointerException("unknown mail ");
        }
        Cache.getBroadSet().add(board);
        Map<String, RegInfo> boardMap = Cache.getRegMap().get(board);
        if (boardMap == null) {
            boardMap = new HashMap<>();
        }
        RegInfo info = new RegInfo(board, title, author, mail);
        boardMap.put(info.getKey(), info);
        Cache.getRegMap().put(board, boardMap);
        TimerInitial.getInstance();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(Exception ex) {
        return ex.getMessage();
    }
}
