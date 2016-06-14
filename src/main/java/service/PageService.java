package service;

import dto.PageDTO;
import dto.PostDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DBUtil;

/**
 *
 * @author Sarkhan Rasullu
 */
public class PageService {

    public static List<PageDTO> getUnProcessedPages() {
        List<PageDTO> pages = new ArrayList<PageDTO>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement("select * from page where date_processed is null ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                pages.add(new PageDTO(rs));
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, rs, stmt);
        }
        return pages;
    }

    private static final Logger LOG = Logger.getLogger(PageService.class.getName());

    public static void setAsProcessed(int pageId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement("update page set date_processed = now() where id = ? ");
            stmt.setInt(1, pageId);
            stmt.executeUpdate();
        } finally {
            closeAll(conn, null, stmt);
        }
    }

    public static void closeAll(Connection conn, ResultSet rs, Statement stmt) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
