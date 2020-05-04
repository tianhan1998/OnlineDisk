package entity;

public class Directory {
    private String dName;
    private String path;

    @Override
    public String toString() {
        return "Directory{" +
                "dName='" + dName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }
}
