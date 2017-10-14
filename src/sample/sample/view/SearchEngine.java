package sample.sample.view;

/**
 * samixx project
 *
 * @author Salman Samie
 *         21 Aug, 2016
 */

public class SearchEngine {

    private String short_name;
    private String keyword;
    private String url;

    public SearchEngine(String short_name, String keyword, String url) {
        super();
        this.short_name = short_name;
        this.keyword = keyword;
        this.url = url;
    }

    //getters
    public String getShort_name() {
        return short_name;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getUrl() {
        return url;
    }

    //setters
    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
