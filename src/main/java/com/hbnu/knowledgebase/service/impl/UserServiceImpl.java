package com.hbnu.knowledgebase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.knowledgebase.entity.User;
import com.hbnu.knowledgebase.service.UserService;
import com.hbnu.knowledgebase.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author TX
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-05-07 17:40:13
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




