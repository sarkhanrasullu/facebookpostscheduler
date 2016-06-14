package timertasks;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import config.Config;
import dto.PostDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.PostService;
import static util.FileUtil.downloadFile;

public class FacebookPoster extends Thread {

    PostDTO post = null;
    private static final Logger LOG = Logger.getLogger(FacebookPoster.class.getName());

    public FacebookPoster(PostDTO post) {
        this.post = post;
    }

    public void postMessage(PostDTO post) throws Exception {
        FacebookType publishMessageResponse = null;
        FacebookClient facebookClient = new DefaultFacebookClient(Config.pageaccesstoken, Config.appsecret, Version.LATEST);
        if (post.getFileType() == 1) {
            publishMessageResponse = facebookClient.publish(Config.pageid + "/photos",
                    FacebookType.class,
                    BinaryAttachment.with(post.getFileName(), downloadFile(post.getUrl()))
            );
        } else if (post.getFileType() == 2) {
            //gif must be here
        } else if (post.getFileType() == 3) {
            publishMessageResponse = facebookClient.publish(Config.pageid + "/videos",
                    FacebookType.class,
                    BinaryAttachment.with(post.getFileName(), downloadFile(post.getUrl())),
                    Parameter.with("title", post.getTitle()),
                    Parameter.with("description", post.getDescription())
            );
        }
        System.out.println(publishMessageResponse);
    }

    @Override
    public void run() {
        try {
            postMessage(post);
            PostService.setAsProcessed(post.getId());
        } catch (Exception ex) {
            PostService.increaseErrorCount(post.getId());
            Logger.getLogger(FacebookPoster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
