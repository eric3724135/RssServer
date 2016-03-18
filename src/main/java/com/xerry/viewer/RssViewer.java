package com.xerry.viewer;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.xerry.model.FeedMsg;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by eric on 2016/3/18.
 */
public class RssViewer extends AbstractRssFeedView {


    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<FeedMsg> listContent = (List<FeedMsg>) model.get("feedContent");
        List<Item> items = new ArrayList<Item>(listContent.size());

        for (FeedMsg msg : listContent) {

            Item item = new Item();

            Content content = new Content();
            content.setValue(msg.getDescription());
            item.setContent(content);

            item.setTitle(msg.getTitle());
            item.setLink(msg.getLink());
            item.setPubDate(msg.getPubDate());
            item.setAuthor(msg.getAuthor());
            Guid guid = new Guid();
            guid.setPermaLink(true);
            guid.setValue(msg.getLink());
            item.setGuid(guid);
            items.add(item);
        }
        return items;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle("PTT RSS");
        feed.setDescription("Parse PTT ");
        feed.setLink("http://www.mkyong.com");

        super.buildFeedMetadata(model, feed, request);
    }
}
