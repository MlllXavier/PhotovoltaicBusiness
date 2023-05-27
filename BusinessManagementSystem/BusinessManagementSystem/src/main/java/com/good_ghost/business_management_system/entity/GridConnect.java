package com.good_ghost.business_management_system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Xavier
 * @since 2022-07-20
 */
@Getter
@Setter
@TableName("grid_connect")
public class GridConnect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 户号
     */
    private String houseId;

    /**
     * 并网申请
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime addApply;

    /**
     * 现场勘察
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime addSurvey;

    /**
     * 接入方案设计及确认
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime addConfirm;

    /**
     * 项目备案
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime addRecord;

    /**
     * 并网验收与调试申请
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime connectApply;

    /**
     * 安装计量装置
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime connectDevice;

    /**
     * 签署合同及协议
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime connectSign;

    /**
     * 并网验收与调试
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime connectCheck;

    /**
     * 是否并网运行
     */
    private Integer isComplete;

    /**
     * 备注
     */
    private String notes;

    /**
     * 文件路径
     */
    private String files;

    private String process1Number;

    private String process7Number;

    private String process1Name;
    private String process2Name;
    private String process3Name;
    private String process4Name;
    private String process5Name;
    private String process6Name;

    private String process7Name;


}
