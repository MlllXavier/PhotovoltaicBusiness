package com.good_ghost.business_management_system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
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
public class Compensation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String subsidyId;

    /**
     * 户号
     */
    private String houseId;

    /**
     * 户名
     */
    private String houseName;

    /**
     * 开始日期
     */
    private LocalDate timeBegin;

    /**
     * 结束日期
     */
    private LocalDate timeFinish;

    /**
     * 错误单价
     */
    private Double errorFee;

    /**
     * 正确单价
     */
    private Double rightFee;

    /**
     * 总发电量
     */
    private Double generatingQuantity;

    /**
     * 总追补金额
     */
    private Double compensationFee;

    private LocalDate subsidyTime;

    private Double subsidyFee;

    private Double subsidyTotalFee;

    private Double subsidyRestFee;

    /**
     * 备注
     */
    private String notes;

    /**
     * 该数据创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 该数据修改时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
