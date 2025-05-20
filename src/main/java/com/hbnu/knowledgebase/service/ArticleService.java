package com.hbnu.knowledgebase.service;

import com.hbnu.knowledgebase.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author TX
* @description 针对表【article(文章版本表)】的数据库操作Service
* @createDate 2025-05-07 17:39:39
*/
public interface ArticleService extends IService<Article> {

    Article selectByIdAndVersion(int articleNum, int version);

    void incrementView(Long articleId);
    void incrementLike(Long articleId);

    void publishNewVersion(Article article);


    void setCurrentVersion(int articleNum, Long articleId);

}
