package com.sjqp.driverexame.entity.dto;

import com.sjqp.driverexame.entity.UserInfo;

public class UserInfoDto extends UserInfo{

    private String role;

    private String newPwd;

    private String oldPwd;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }
}
