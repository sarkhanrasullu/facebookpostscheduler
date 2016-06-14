package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Sarkhan Rasullu
 */
public class PageDTO {

    private int id;
    private String url;
    private Date dateProcessed;

    public PageDTO() {
    }

    public PageDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.url = rs.getString("url");
        this.dateProcessed = rs.getDate("date_processed");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

}
