package parser;

import dto.PageDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.PageService;

/**
 *
 * @author Sarkhan Rasullu
 */
public class PageParserCenter extends Thread {

    private static final Logger LOG = Logger.getLogger(PageParserCenter.class.getName());

    @Override
    public void run() {
        while (true) {
            try {
                List<PageDTO> list = PageService.getUnProcessedPages();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        PageDTO page = list.get(i);
                        PageService.setAsProcessed(page.getId());
                        new Thread(new PageParser(page.getUrl())).start();
                    }
                }
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            } finally {
                sleep_(5000);
            }
        }
    }

    public void sleep_(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception ex) {
            Logger.getLogger(PageParserCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
