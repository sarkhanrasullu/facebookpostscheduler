package parser;

import dto.PostDTO;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.xml.sax.SAXException;
import service.PostService;

public class PageParser implements Runnable {

    String url;

    public PageParser(String url) {
        this.url = url;
    }

    public void parseAndProcessVideos(String url) {
        try {
            url = url.trim();
            Connection conn = Jsoup.connect(url).timeout(10000);
            Document doc = conn.get();
            Elements listVideos = doc.getElementsByClass("play-icon");
            if (listVideos != null) {
                for (int i = 0; i < listVideos.size(); i++) {
                    try {
                        System.out.println("url=" + listVideos.get(i).attr("href"));
                        PostDTO post = parseVideo(listVideos.get(i).attr("href"));
                        PostService.insertDatabase(post);
                    } catch (Exception ex) {
                        Logger.getLogger(PageParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PageParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PostDTO parseVideo(String url) throws Exception {
        url = url.trim();

        Connection conn = Jsoup.connect(url).timeout(10000);
        Document doc = conn.get();

        Element title = doc.getElementsByClass("stuff-title").get(0);
        Element desc = doc.getElementsByClass("stuff-desc").get(0);
        Element stuffPlayer = doc.getElementById("stuff-player");
        String[] downloadArr = stuffPlayer.html().split("\\,");
        String download = downloadArr[4].replace("\"", "").split("file:")[1].trim();
        PostDTO post = new PostDTO();
        String[] extensionArr = download.split("\\.");
        String ext = extensionArr[extensionArr.length-1];
        String fileName = (UUID.randomUUID().toString()+"."+ext).toLowerCase();
        post.setFileName(fileName);
        post.setTitle(title.text());
        post.setDescription(desc.text());
        post.setUrl(download);
        return post;
    }

    public void run() {
        parseAndProcessVideos(url);
    } 

}
