package entity;


public class Good {
    private Integer gid;
    private String username;
    private String common_id;

    @Override
    public String toString() {
        return "Good{" +
                "gid=" + gid +
                ", username='" + username + '\'' +
                ", common_id='" + common_id + '\'' +
                '}';
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommon_id() {
        return common_id;
    }

    public void setCommon_id(String common_id) {
        this.common_id = common_id;
    }
}
