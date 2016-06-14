package timertasks;

import dto.PostDTO;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.PostService;

/**
 *
 * @author Sarkhan Rasullu
 */
public class FacebookPostCenter extends Thread {

    @Override
    public void run() {

        while (true) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            try {
                List<PostDTO> posts = PostService.getUnProcessedPosts();
                System.out.println("post center started.posts size:" + posts.size());
                if (posts != null && posts.size() > 0) {
                    for (PostDTO post : posts) {
                        if (post == null
                                || post.getTitle() == null
                                || post.getTitle().trim().isEmpty()
                                || post.getUrl() == null
                                || post.getUrl().trim().isEmpty()) {
                            continue;
                        }
                        executorService.execute(new FacebookPoster(post));
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(FacebookPostCenter.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    executorService.shutdown();
                    executorService.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FacebookPostCenter.class.getName()).log(Level.SEVERE, null, ex);
                }
                sleep_(1 * 5 * 1000);
            }
        }
    }

    public void sleep_(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(FacebookPostCenter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
