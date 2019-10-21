package entity;

public class MyFile {
    private String username;
    private String filename;

    public String getFakename() {
        return fakename;
    }

    public void setFakename(String fakename) {
        this.fakename = fakename;
    }

    private String fakename;
    private String size;
    private Integer id;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
