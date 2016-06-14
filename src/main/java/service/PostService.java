package service;

import config.Config;
import dto.PostDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DBUtil;

public class PostService {

    public static List<PostDTO> getUnProcessedPosts() {
        List<PostDTO> posts = new ArrayList<PostDTO>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement(
                    "select * from posts "
                    + " where date_processed is null "
                    + " and error_count=0 "
                    + " and date_process < now() ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(new PostDTO(rs));
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, rs, stmt);
        }
        return posts;
    }

    public static void increaseErrorCount(int postId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement("update posts set error_count = error_count+1 where id = ? ");
            stmt.setInt(1, postId);
            stmt.executeUpdate();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, null, stmt);
        }
    }
    private static final Logger LOG = Logger.getLogger(PostService.class.getName());

    public static void setAsProcessed(int postId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement("update posts set date_processed = now() where id = ? ");
            stmt.setInt(1, postId);
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
                Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static long getMaxDate() {
        long result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement(
                    "select max(date_process) from posts ");
            rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp d = rs.getTimestamp(1);
                if (d != null) {
                    result = d.getTime();
                }else{
                    result = new java.util.Date().getTime();
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            closeAll(conn, rs, stmt);
        }
        return result;
    }

    public static void insertDatabase(PostDTO post) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.connectToMySQL();
            stmt = conn.prepareStatement(
                    "insert into posts(file_name,file_type,post_description,"
                    + "post_title,post_url,date_process) values"
                    + "(?,3,?,?,?,?)");
            stmt.setString(1, post.getFileName());
            stmt.setString(2, post.getDescription());
            stmt.setString(3, post.getTitle());
            stmt.setString(4, post.getUrl());
            stmt.setTimestamp(5, new Timestamp(getMaxDate() + Config.postinterval));
            stmt.executeUpdate();

        } finally {
            closeAll(conn, null, stmt);
        }
    }

    public static void main(String[] args) {
        
        System.out.println(new java.util.Date(getMaxDate() + Config.postinterval));
    }
}
