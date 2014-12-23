package hello.Model;

public class UserRole {
    private long Id;
    private long userId;
    private long roleId;

    public UserRole(long Id, long userId,long roleId){
        this.Id = Id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId() {
        return Id;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoleId() {
        return roleId;
    }
}
