package com.hbnu.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbnu.knowledgebase.entity.User;
import com.hbnu.knowledgebase.service.UserService;
import com.hbnu.knowledgebase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 批量查询用户ID对应的用户名
     */
    @PostMapping("/names")
    public ResponseEntity<?> getUserNames(@RequestBody Map<String, List<Integer>> payload) {
        List<Integer> ids = payload.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.ok(Result.success(Collections.emptyMap()));
        }

        List<User> users = userService.list(new QueryWrapper<User>().in("user_id", ids));
        Map<Integer, String> nameMap = users.stream()
                .collect(Collectors.toMap(User::getUserId, User::getUsername));

        return ResponseEntity.ok(Result.success(nameMap));
    }

    /**
     * 获取所有用户列表
     */
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.list();
        //测试
        System.out.println("UserController /list运行：" + users);

        return ResponseEntity.ok(Result.success(users));
    }


    /**
     * 根据 ID 获取用户详情
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return ResponseEntity.badRequest().body(Result.error(400, "无效的用户ID"));
            }

            User user = userService.getById(userId);
            if (user == null) {
                return ResponseEntity.status(404).body(Result.error(404, "用户不存在"));
            }

            return ResponseEntity.ok(Result.success(user));
        } catch (Exception e) {
            e.printStackTrace(); // 打印堆栈信息，方便定位问题
            return ResponseEntity.status(500).body(Result.error(500, "服务器内部错误：" + e.getMessage()));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> testUser() {
        return ResponseEntity.ok(Result.success("测试成功"));
    }

}
