package entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class Common {
    private Integer id;
    private String username;
    @NotEmpty(message = "评论不能为空")
    private String text;
    private long good_number;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date comment_day;
    private boolean good;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getGood_number() {
        return good_number;
    }

    public void setGood_number(long good_number) {
        this.good_number = good_number;
    }

    public Date getComment_day() {
        return comment_day;
    }

    public void setComment_day(Date comment_day) {
        this.comment_day = comment_day;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

}
