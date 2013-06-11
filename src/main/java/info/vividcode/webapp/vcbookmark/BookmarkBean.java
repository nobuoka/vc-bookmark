package info.vividcode.webapp.vcbookmark;

public class BookmarkBean {
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BookmarkBean() {
    }
    public BookmarkBean(String url) {
        this.url = url;
    }
}
