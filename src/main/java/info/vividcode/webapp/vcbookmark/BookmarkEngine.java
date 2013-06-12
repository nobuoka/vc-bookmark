package info.vividcode.webapp.vcbookmark;

import info.vividcode.webapp.vcbookmark.db.DatabaseInitializer;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class BookmarkEngine {

    private DatabaseInitializer mDbManager = new DatabaseInitializer();

    @GET
    @Produces("application/json")
    @Path("bookmarks")
    public Response getBookmarks() throws SQLException {
        List<BookmarkBean> bookmarks = mDbManager.operateDatabase(DatabaseInitializer.selectAllBookmarks, null);
        return Response.ok().entity(bookmarks).build();
    }

    @POST
    @Consumes("application/json")
    @Path("bookmark")
    public Response createBookmark(BookmarkBean input) throws SQLException {
        boolean res = mDbManager.operateDatabase(DatabaseInitializer.insertBookmark, input);
        if (res) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
}
