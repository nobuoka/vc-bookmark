package info.vividcode.webapp.vcbookmark.db;

import info.vividcode.webapp.vcbookmark.BookmarkBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    public static void main(String[] args) throws SQLException {
        DatabaseInitializer dbInitializer = new DatabaseInitializer();
        dbInitializer.operateDatabase(new DatabaseOperation<Void, Void>() {
            @Override public Void call(Connection conn, Void args) throws SQLException {
                createBookmarkTable(conn);
                return null;
            }
        }, null);
    }

    interface DatabaseOperation<TR,TA> {
        TR call(Connection conn, TA args) throws SQLException;
    }

    public <TR,TA> TR operateDatabase(DatabaseOperation<TR,TA> op, TA arg) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                "jdbc:derby:bookmark;create=true"
            );
            return op.call(conn, arg);
        } catch(SQLException se) {
            for (SQLException e = se; e != null; e = e.getNextException()) {
                System.err.printf("%s: %s%n", e.getSQLState(), e.getMessage());
            }
            throw se;
        } finally {
            if (conn != null) conn.close();
        }
    }

    private static void createBookmarkTable(Connection conn) throws SQLException {
        String tableCreationQuery =
                "CREATE TABLE bookmark (" +
                    "url varchar(4096) NOT NULL," +
                    "uid int NOT NULL," +
                    "PRIMARY KEY (url, uid)" +
                ")";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(tableCreationQuery);
        stmt.close();
    }

    public static final DatabaseOperation<List<BookmarkBean>, Void> selectAllBookmarks =
    new DatabaseOperation<List<BookmarkBean>,Void>() {
        @Override public List<BookmarkBean> call(Connection conn, Void nouse) throws SQLException {
            // SQLコンテナの作成
            Statement stmt = conn.createStatement();
            // SQL文の実行
            String sql = "SELECT * FROM bookmark";
            ResultSet rs = stmt.executeQuery(sql);

            // 検索結果の取り出し
            List<BookmarkBean> bookmarks = new ArrayList<BookmarkBean>();
            while(rs.next()) {
                BookmarkBean b = new BookmarkBean(rs.getString("url"));
                bookmarks.add(b);
            }

            // 検索結果のクローズ
            rs.close();
            // SQLコンテナのクローズ
            stmt.close();

            return bookmarks;
        }
    };

    public static final DatabaseOperation<Boolean, BookmarkBean> insertBookmark =
    new DatabaseOperation<Boolean,BookmarkBean>() {
        @Override public Boolean call(Connection conn, BookmarkBean bookmark) throws SQLException {
            // SQL文の実行
            String sql = "INSERT INTO bookmark (url,uid) VALUES (?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, bookmark.getUrl());
            stat.setInt(2, 0);
            stat.executeUpdate();
            // SQLコンテナのクローズ
            stat.close();

            return true;
        }
    };
}
