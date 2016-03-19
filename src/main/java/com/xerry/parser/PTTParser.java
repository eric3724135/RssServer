package com.xerry.parser;


import com.xerry.cache.Cache;
import com.xerry.constent.Constant;
import com.xerry.model.FeedMsg;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.*;

/**
 * Created by eric on 2016/3/18.
 */
@Service
public class PTTParser {
    private static final Logger log = Logger.getLogger(PTTParser.class);

    private List<FeedMsg> result = new ArrayList<>();

    public void getNews(String board) throws KeyManagementException, NoSuchAlgorithmException {
        result = new ArrayList<>();
        String url = Constant.BASE_URL + Constant.BBS + board + Constant.POSTFIX_URL;
        while (result.size() < 50) {
            url = this.parse(board, url);
        }
        Cache.getPttCache().put(board, result);
    }


    private String parse(String board, String url) throws KeyManagementException, NoSuchAlgorithmException {
        String nextPage = "";
        enableSSL();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements lis = doc.select("#main-container > div.r-list-container.bbs-screen > div");
            Collections.reverse(lis);
            for (Element li : lis) {
                try {
                    FeedMsg msg = new FeedMsg();
                    if (li.children() != null) {
                        Element check = li.child(2);
                        if (check.children() != null) {
                            Element title = check.child(0);
                            msg.setTitle(title.text());
                            msg.setLink(title.attr("href"));
                            msg.setGuid(title.attr("href"));
                            msg.setDescription(title.text());
                            msg.setPubDate(new Date());
                            Element author = li.child(3).child(1);
                            msg.setAuthor(author.text());
                            result.add(msg);
                        }
                    }
                } catch (Exception e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
            //get previous page
            Elements actions = doc.select("#action-bar-container > div.action-bar > div.btn-group.pull-right > a:eq(1)");
            for (Element li : actions) {
                try {
                    nextPage = Constant.BASE_URL + li.attr("href");
                } catch (Exception e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextPage;
    }


    private void enableSSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    public static void main(String[] args) {
        PTTParser parser = new PTTParser();
        try {
            parser.getNews("Stock");
            log.info(Cache.getPttCache().get("Stock").size());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
