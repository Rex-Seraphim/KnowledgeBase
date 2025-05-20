package com.hbnu.knowledgebase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.knowledgebase.entity.Article;
import com.hbnu.knowledgebase.service.ArticleService;
import com.hbnu.knowledgebase.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author TX
* @description 针对表【article(文章版本表)】的数据库操作Service实现
* @createDate 2025-05-07 17:39:39
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{


    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article selectByIdAndVersion(int articleNum, int version) {
        return null;
    }


    @Override
    public void incrementView(Long articleId) {
        articleMapper.incrementView(articleId);
    }

    @Override
    public void incrementLike(Long articleId) {
        articleMapper.incrementLike(articleId);
    }

    @Override
    public void publishNewVersion(Article article) {
        // 获取当前最大版本号
        Integer currentVersion = articleMapper.selectCurrentVersion(article.getArticleNum());

        // 查询原文章信息用于构造新版本
        Article oldArticle = articleMapper.selectByIdAndVersion(article.getArticleNum(), currentVersion);

        if (oldArticle == null) {
            throw new RuntimeException("找不到原始文章");
        }

        // 构造新文章记录
        Article newArticle = new Article();
        newArticle.setArticleNum(oldArticle.getArticleNum());
        newArticle.setKbId(oldArticle.getKbId());
        newArticle.setChapter(article.getChapter());
        newArticle.setTitle(article.getTitle());
        newArticle.setContent(article.getContent());
        newArticle.setAuthorId(oldArticle.getAuthorId());
        newArticle.setVersion(currentVersion == null ? 1 : currentVersion + 1);
        newArticle.setIsCurrent(1); // 新版本为当前版本

        // 插入新文章
        articleMapper.insert(newArticle);

        // 将旧版本设为非当前
        articleMapper.deactivateOldVersions(article.getArticleNum(), newArticle.getArticleId());
    }

    @Override
    public void setCurrentVersion(int articleNum, Long articleId) {
        // 先将所有该 article_num 的版本设置为非当前
        articleMapper.deactivateAllVersions(articleNum);

        // 再将指定版本设置为当前
        articleMapper.activateVersion(articleId);
    }


}




