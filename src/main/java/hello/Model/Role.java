package hello.Model;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 23.12.14
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class Role {
    private long Id;
    private String Name;

    public Role(long id,String name)
    {
        this.Id = id;
        this.Name = name;
    }

    public long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
}
