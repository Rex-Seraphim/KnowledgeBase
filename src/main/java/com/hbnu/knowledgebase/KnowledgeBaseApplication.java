package com.hbnu.knowledgebase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
@MapperScan("com.hbnu.knowledgebase.mapper")        //包扫描，扫描mapper接口(否则无法找到mapper)
public class KnowledgeBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeBaseApplication.class, args);
    }

}



