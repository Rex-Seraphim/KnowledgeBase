package com.hbnu.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 状态（0活跃，1注销）
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private Date lastLogin;

    /**
     * 用户邮箱
     */
    private String email;

    public User() {
    }

    public User(Integer userId, String username, String password, Date createdAt, Integer status, Date lastLogin, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.status = status;
        this.lastLogin = lastLogin;
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(createdAt, user.createdAt) && Objects.equals(status, user.status) && Objects.equals(lastLogin, user.lastLogin) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, createdAt, status, lastLogin, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", lastLogin=" + lastLogin +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}