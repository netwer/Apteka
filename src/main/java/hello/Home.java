package hello;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class Home {
    private final long id;
    private final String content;
    public Home(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
