package info.vividcode.webapp.vcbookmark;

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
    @Path("api/bookmarks")
    public Response getBookmarks() {
        BookmarkBean bookmark = new BookmarkBean("http://www.vividcode.info/");
        return Response.ok().entity(bookmark).build();
    }

    @POST
    @Consumes("application/json")
    @Path("api/bookmark")
    public Response createBookmark(BookmarkBean input) {
        System.out.println(input.getUrl());
        return Response.ok().build();
    }

}
