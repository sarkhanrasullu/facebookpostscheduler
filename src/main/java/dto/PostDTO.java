package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Sarkhan Rasullu
 */
public class PostDTO {

    private int id;
    private String title;
    private String url;
    private Date dateProcess;
    private Date dateProcessed;
    private Date dateAdded;
    private int errorCount;
    private int fileType;
    private String fileName;
    private String description;
    
    public PostDTO() {
    }

    public PostDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.title = rs.getString("post_title");
        this.url = rs.getString("post_url");
        this.dateProcess = rs.getDate("date_process");
        this.dateProcessed = rs.getDate("date_processed");
        this.dateAdded = rs.getDate("date_added");
        this.errorCount = rs.getInt("error_count");
        this.fileName = rs.getString("file_name");
        this.description = rs.getString("post_description");
        this.fileType = rs.getInt("file_type");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    

    public Date getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(Date dateProcess) {
        this.dateProcess = dateProcess;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PostDTO{" + "id=" + id + ", title=" + title + ", url=" + url + ", dateProcess=" + dateProcess + ", dateProcessed=" + dateProcessed + ", dateAdded=" + dateAdded + ", errorCount=" + errorCount + ", fileType=" + fileType + ", fileName=" + fileName + ", description=" + description + '}';
    }

    
    
}
