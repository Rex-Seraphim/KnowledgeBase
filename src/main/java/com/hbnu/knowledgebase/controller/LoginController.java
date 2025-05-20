package com.hbnu.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbnu.knowledgebase.entity.LoginRequest;
import com.hbnu.knowledgebase.entity.User;
import com.hbnu.knowledgebase.service.UserService;
import com.hbnu.knowledgebase.util.JwtUtil;
import com.hbnu.knowledgebase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<Result<Map<String, Object>>> login(@RequestBody LoginRequest request) {
        //测试是否收到请求
        System.out.println("收到登录请求：\nusername:" + request.getUsername() + "\npassword:" + request.getPassword());


        String username = request.getUsername();
        String password = request.getPassword();

        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));

        System.out.println("查到用户：" + user);

        if (user == null || !user.getPassword().equals(password)) {

            //测试是否通过验证
            System.out.println("用户名或密码错误");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Result.error(401, "用户名或密码错误"));
        }

        // 生成 JWT Token 或 Session ID
        String token = generateToken(user);

        Map<String, Object> data = Map.of(
                "token", token,
                "user", user
        );

        return ResponseEntity.ok()
                .body(Result.success(data));
    }



    // 登出接口（清除 Token）
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("已退出");
    }

    // 校验是否登录的接口
    @GetMapping("/check-login")
    public ResponseEntity<?> checkLogin() {
        // 这里可以结合拦截器判断用户是否登录
        return ResponseEntity.ok().build();
    }

    private String generateToken(User user) {
        // 使用 JWT 工具类生成 token
        return JwtUtil.sign(user.getUserId().toString(), user.getUsername());
    }
}




