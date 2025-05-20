package com.hbnu.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbnu.knowledgebase.entity.Article;
import com.hbnu.knowledgebase.entity.KnowledgeBase;
import com.hbnu.knowledgebase.service.KnowledgeBaseService;
import com.hbnu.knowledgebase.service.ArticleService;
import com.hbnu.knowledgebase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kb")
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private ArticleService articleService;

    // 获取所有知识库
    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        List<KnowledgeBase> list = knowledgeBaseService.list();
        return ResponseEntity.ok(Result.success(list));
    }

    // 获取某个知识库下的所有最新文章
    @GetMapping("/{kbId}/articles")
    public ResponseEntity<?> getArticles(@PathVariable Integer kbId) {
        List<Article> articles = articleService.list(new QueryWrapper<Article>()
                .eq("kb_id", kbId)
                .eq("is_current", true));
        return ResponseEntity.ok(Result.success(articles));
    }


}
