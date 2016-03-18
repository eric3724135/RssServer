package com.xerry.controller;

import com.xerry.model.FeedMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eric on 2016/3/18.
 */
@Controller
public class RssController {

    @RequestMapping(value = "/rss/{board}", method = RequestMethod.GET)
    public ModelAndView getFeedInRss(@PathVariable("board") String board) {
        List<FeedMsg> items = new ArrayList<FeedMsg>();

        FeedMsg feedMsg = new FeedMsg();
        feedMsg.setTitle("Spring MVC Tutorial 1");
        feedMsg.setLink("http://www.mkyong.com/spring-mvc/tutorial-1");
        feedMsg.setDescription("Tutorial 1 summary ...");
        feedMsg.setPubDate(new Date());
        items.add(feedMsg);

        FeedMsg feedMsg2 = new FeedMsg();
        feedMsg2.setTitle("Spring MVC Tutorial 2");
        feedMsg2.setLink("http://www.mkyong.com/spring-mvc/tutorial-2");
        feedMsg2.setDescription("Tutorial 2 summary ...");
        feedMsg2.setPubDate(new Date());
        items.add(feedMsg2);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("rssViewer");
        mav.addObject("feedContent", items);

        return mav;
    }
}
