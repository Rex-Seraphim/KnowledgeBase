package com.hbnu.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbnu.knowledgebase.entity.User;
import com.hbnu.knowledgebase.service.UserService;
import com.hbnu.knowledgebase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        // 检查用户名是否已存在
        if (userService.getOne(new QueryWrapper<User>().eq("username", username)) != null) {
            return ResponseEntity.badRequest()
                    .body(Result.error(400, "用户名已存在"));
        }

        // 创建用户并填充字段
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setStatus(0); // 默认状态：活跃

        Date now = new Date(); // 获取当前时间
        user.setCreatedAt(now); // 设置注册时间
        user.setLastLogin(now); // 初始登录时间与注册时间一致

        // 保存用户
        userService.save(user);

        return ResponseEntity.ok(Result.success("注册成功"));
    }
}




