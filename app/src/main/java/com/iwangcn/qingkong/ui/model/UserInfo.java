package com.iwangcn.qingkong.ui.model;

/**
 * 用户模型
 * Created by czh on 2017/4/17.
 */

public class UserInfo {
    private long autoId;
    private String name;//账号
    private String nickName;//昵称
    private String password;//密码
    private String telephone;// 手机号
    private String email;//邮箱
    private int type;//0表示超级管理员，1表示主管，2表示业务人员，3表示客户
    private int status;//状态
    private long updateTime;//更新时间

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
