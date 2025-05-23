package com.hbnu.knowledgebase.mapper;

import com.hbnu.knowledgebase.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author TX
* @description 针对表【article(文章版本表)】的数据库操作Mapper
* @createDate 2025-05-07 17:39:39
* @Entity com.hbnu.knowledgebase.empty.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    // 自定义更新 view_count 和 like_count 的方法
    int incrementView(Long articleId);
    int incrementLike(Long articleId);

    /**
     * 获取某 article_num 的最大版本号
     */
    Integer selectCurrentVersion(int articleNum);

    Article selectByIdAndVersion(@Param("articleNum") int articleNum, @Param("version") Integer version);

    /**
     * 设置旧版本为非当前版本
     */
    int deactivateOldVersions(@Param("articleNum") int articleNum, @Param("newId") Long newId);

    /**
     * 批量关闭某 article_num 的所有版本
     */
    int deactivateAllVersions(int articleNum);

    /**
     * 激活某个版本（设置 is_current = 1）
     */
    int activateVersion(Long articleId);


    // 获取最大的 article_num
    Integer selectMaxArticleNum();

    // 获取指定 article_num 的最大版本号
    Integer selectMaxVersionByNum(@Param("articleNum") int articleNum);

    // 更新某个 article_num 下所有记录的 is_current 为 0
    void updateIsCurrentToZero(@Param("articleNum") int articleNum);



}




