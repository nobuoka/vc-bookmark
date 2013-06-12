package info.vividcode.webapp.vcbookmark;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class BookmarkEngine {

    @GET
    @Produces("application/json")
    @Path("bookmarks")
    public Response getBookmarks() {
        List<BookmarkBean> bookmarks = new ArrayList<BookmarkBean>();
        BookmarkBean bookmark = new BookmarkBean("http://www.vividcode.info/");
        bookmarks.add(bookmark);
        return Response.ok().entity(bookmarks).build();
    }

    @POST
    @Consumes("application/json")
    @Path("bookmark")
    public Response createBookmark(BookmarkBean input) {
        System.out.println(input.getUrl());
        return Response.ok().build();
    }
}
