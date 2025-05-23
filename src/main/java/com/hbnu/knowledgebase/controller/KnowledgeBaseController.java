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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    // 创建知识库
    @PostMapping
    public ResponseEntity<?> create(@RequestBody KnowledgeBase knowledgeBase) {
        // 自动填充创建时间
        if (knowledgeBase.getCreatedAt() == null || knowledgeBase.getCreatedAt().isEmpty()) {
            knowledgeBase.setCreatedAt(currentTimeString());
        }
        knowledgeBaseService.save(knowledgeBase);
        return ResponseEntity.ok(Result.success("知识库创建成功"));
    }

    // 发布新版本文章（Article）
    @PostMapping("/publish")
    public ResponseEntity<?> publishNewVersion(@RequestBody Article article) {
        // 自动填充字段
        if (article.getAuthorId() == null) {
            return ResponseEntity.badRequest().body(Result.error(400, "作者ID不能为空"));
        }
        if (article.getCreatedAt() == null || article.getCreatedAt().isEmpty()) {
            article.setCreatedAt(currentTimeString());
        }

        articleService.publishNewVersion(article);
        return ResponseEntity.ok(Result.success("新版本已发布"));
    }

    // 辅助方法：生成当前时间字符串（ISO 格式）
    private String currentTimeString() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
