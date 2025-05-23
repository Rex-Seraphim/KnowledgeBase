package com.hbnu.knowledgebase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 知识库表
 * @TableName knowledge_base
 */
@TableName(value ="knowledge_base")
@Data
public class KnowledgeBase {
    /**
     * 知识库ID
     */
    @TableId(type = IdType.AUTO)
    private Integer kbId;

    /**
     * 知识库名称
     */
    private String kbName;

    /**
     * 创建者ID
     */
    private Integer creatorId;

    /**
     * 创建时间
     */
    private String createdAt;

    public KnowledgeBase() {
    }

    public KnowledgeBase(Integer kbId, String kbName, Integer creatorId, String createdAt) {
        this.kbId = kbId;
        this.kbName = kbName;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
    }

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
        KnowledgeBase other = (KnowledgeBase) that;
        return (this.getKbId() == null ? other.getKbId() == null : this.getKbId().equals(other.getKbId()))
            && (this.getKbName() == null ? other.getKbName() == null : this.getKbName().equals(other.getKbName()))
            && (this.getCreatorId() == null ? other.getCreatorId() == null : this.getCreatorId().equals(other.getCreatorId()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getKbId() == null) ? 0 : getKbId().hashCode());
        result = prime * result + ((getKbName() == null) ? 0 : getKbName().hashCode());
        result = prime * result + ((getCreatorId() == null) ? 0 : getCreatorId().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", kbId=").append(kbId);
        sb.append(", kbName=").append(kbName);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}