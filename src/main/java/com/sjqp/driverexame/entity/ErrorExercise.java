package com.sjqp.driverexame.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 错题练习
 * @author qinpeng on 2018/12/26
 */
@Entity
public class ErrorExercise {

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
        return "ErrorExercise{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                ", answer='" + answer + '\'' +
                ", choice='" + choice + '\'' +
                '}';
    }

    public ErrorExercise() {
    }
}
