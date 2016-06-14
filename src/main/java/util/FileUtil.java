package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.server.UID;
import java.util.UUID;
//import org.apache.commons.io.FileUtils;
//import org.apache.tika.Tika;
import sun.misc.IOUtils;

/**
 *
 * @author Sarkhan Rasullu
 */
public class FileUtil {

//    public static String detectFileExtension(byte[] bytes) throws IOException {
//        Tika tika = new Tika();
//        File f = new File(UUID.randomUUID().toString());
//        FileUtils.writeByteArrayToFile(f, bytes);
//        //detecting the file type using detect method
//        String filetype = tika.detect(f);
//        return filetype;
//    }

    public static byte[] downloadFile(String url) throws IOException {
        System.out.println(url);
        URL urlStr = new URL(url);

        return IOUtils.readFully(urlStr.openStream(), -1, false);
    } 
}
