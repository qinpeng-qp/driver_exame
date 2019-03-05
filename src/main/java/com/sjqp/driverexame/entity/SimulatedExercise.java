package com.sjqp.driverexame.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 模拟练习实体
 * @author qinpeng
 */
@Entity
public class SimulatedExercise implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="JDBC")
    private Integer id;

    /**
     * 题号
     */
    private Integer questionId;
    /**
     * 题目详细信息
     */
    private String comment;
    /**
     * 题目创建时间
     */
    private Date createTime;
    /**
     * 题目标准答案
     */
    private String answer;
    /**
     * 题目选项
     */
    private String choice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    @Override
    public String toString() {
        return "SimulatedExercise{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                ", answer='" + answer + '\'' +
                ", choice='" + choice + '\'' +
                '}';
    }

    public SimulatedExercise() {
    }
}
