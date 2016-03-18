package com.xerry.parser;


import com.xerry.constent.Constant;
import com.xerry.model.FeedMsg;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class PTTParser {
    private static final Logger log = Logger.getLogger(PTTParser.class);

    public List<FeedMsg> getNews(String board) throws KeyManagementException, NoSuchAlgorithmException {
        List<FeedMsg> result = new ArrayList<>();
        String url = Constant.BASE_URL + board + Constant.POSTFIX_URL;
        enableSSL();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements lis = doc.select("#main-container > div.r-list-container.bbs-screen > div");

            Collections.reverse(lis);
            for (Element li : lis) {
                try {
                    FeedMsg msg = new FeedMsg();
                    Element title = li.child(2).child(0);
                    msg.setTitle(title.text());
                    msg.setLink(title.attr("href"));
                    msg.setGuid(title.attr("href"));
                    msg.setDescription(title.text());
                    msg.setPubDate(new Date());
                    Element author = li.child(3).child(1);
                    msg.setAuthor(author.text());
                    result.add(msg);
                } catch (Exception e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
            List<FeedMsg> result = parser.getNews("Stock");
            log.info(result.size());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
