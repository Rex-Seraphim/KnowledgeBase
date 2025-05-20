package com.hbnu.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbnu.knowledgebase.entity.Article;
import com.hbnu.knowledgebase.service.ArticleService;
import com.hbnu.knowledgebase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章管理接口控制器
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 根据文章编号和版本号获取文章详情
     */
    @GetMapping("/{articleNum}/version/{version}")
    public ResponseEntity<Article> getArticleByVersion(
            @PathVariable int articleNum,
            @PathVariable int version) {
        return ResponseEntity.ok(articleService.selectByIdAndVersion(articleNum, version));
    }

    /**
     * 增加文章浏览数
     */
    @PutMapping("/{articleId}/view")
    public ResponseEntity<?> incrementView(@PathVariable Long articleId) {
        articleService.incrementView(articleId);
        return ResponseEntity.ok(Result.success("浏览数已增加"));
    }

    /**
     * 增加文章点赞数
     */
    @PutMapping("/{articleId}/like")
    public ResponseEntity<?> incrementLike(@PathVariable Long articleId) {
        articleService.incrementLike(articleId);
        return ResponseEntity.ok(Result.success("点赞数已增加"));
    }

    /**
     * 获取单个文章详情（用于编辑）
     */
    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long articleId) {
        Article article = articleService.getById(articleId);
        return ResponseEntity.ok(article);
    }

    /**
     * 发布新版本文章
     */
    @PostMapping("/publish")
    public ResponseEntity<?> publishNewVersion(@RequestBody Article article) {
        articleService.publishNewVersion(article);
        return ResponseEntity.ok(Result.success("新版本已发布"));
    }

    /**
     * 获取某篇文章的历史版本列表
     */
    @GetMapping("/{articleNum}/versions")
    public ResponseEntity<List<Article>> getArticleVersions(@PathVariable int articleNum) {
        List<Article> versions = articleService.list(new QueryWrapper<Article>()
                .eq("article_num", articleNum)
                .orderByDesc("version"));
        return ResponseEntity.ok(versions);
    }

    /**
     * 设置某个版本为当前版本
     */
    @PutMapping("/{articleNum}/set-current/{articleId}")
    public ResponseEntity<?> setCurrentVersion(
            @PathVariable int articleNum,
            @PathVariable Long articleId) {
        articleService.setCurrentVersion(articleNum, articleId);
        return ResponseEntity.ok(Result.success("当前版本已更新"));
    }

    /**
     * 获取所有当前展示的文章（is_current = 1）
     */
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentArticles() {
        try {
            List<Article> currentArticles = articleService.list(new QueryWrapper<Article>().eq("is_current", 1));
            return ResponseEntity.ok(Result.success(currentArticles));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Result.error(500, "服务器内部错误：" + e.getMessage()));
        }
    }
}
