package com.sjqp.driverexame.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qinpeng on 2018/12/26
 * 管理员实体类
 */
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="JDBC")
    Integer id;
    /**
     * 用户名
     */
    @Column
    private String userName;
    /**
     * 密码
     */
    @Column
    private String password;
    /**
     * 权限
     */
    @Column
    private String priority;
    /**
     * 创建时间
     */
    @Column
    private Date createTime;
    /**
     * 手机号
     */
    @Column
    private String tel;

    public Admin(){}

    public Admin(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", priority='" + priority + '\'' +
                ", createTime=" + createTime +
                ", tel='" + tel + '\'' +
                '}';
    }
}
