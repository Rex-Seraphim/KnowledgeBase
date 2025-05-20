package com.hbnu.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 文章版本表
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article {
    /**
     * 数据库自增主键（MyBatis Plus 使用）
     */
    @TableId(type = IdType.AUTO)
    private Long articleId;

    /**
     * 文章业务编号（非主键）
     */
    private Integer articleNum;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 所属知识库ID
     */
    private Integer kbId;

    /**
     * 章节名称
     */
    private String chapter;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容（支持Markdown格式）
     */
    private String content;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 是否当前展示版本（0否，1是）
     */
    private Integer isCurrent;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Article other = (Article) that;
        return (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getKbId() == null ? other.getKbId() == null : this.getKbId().equals(other.getKbId()))
            && (this.getChapter() == null ? other.getChapter() == null : this.getChapter().equals(other.getChapter()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAuthorId() == null ? other.getAuthorId() == null : this.getAuthorId().equals(other.getAuthorId()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getViewCount() == null ? other.getViewCount() == null : this.getViewCount().equals(other.getViewCount()))
            && (this.getLikeCount() == null ? other.getLikeCount() == null : this.getLikeCount().equals(other.getLikeCount()))
            && (this.getIsCurrent() == null ? other.getIsCurrent() == null : this.getIsCurrent().equals(other.getIsCurrent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getKbId() == null) ? 0 : getKbId().hashCode());
        result = prime * result + ((getChapter() == null) ? 0 : getChapter().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAuthorId() == null) ? 0 : getAuthorId().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getViewCount() == null) ? 0 : getViewCount().hashCode());
        result = prime * result + ((getLikeCount() == null) ? 0 : getLikeCount().hashCode());
        result = prime * result + ((getIsCurrent() == null) ? 0 : getIsCurrent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", articleId=").append(articleId);
        sb.append(", version=").append(version);
        sb.append(", kbId=").append(kbId);
        sb.append(", chapter=").append(chapter);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", authorId=").append(authorId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", viewCount=").append(viewCount);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", isCurrent=").append(isCurrent);
        sb.append("]");
        return sb.toString();
    }
}