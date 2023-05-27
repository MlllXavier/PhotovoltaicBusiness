package com.good_ghost.business_management_system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
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
@TableName("subsidy")
public class Subsidy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
      @TableId(value = "id", type = IdType.AUTO)
      @ExcelIgnore
    private Integer id;

    /**
     * 项目编号
     */
    @ExcelProperty("项目编号")
    @ColumnWidth(10)
    private String houseId;

    /**
     * 项目名称
     */
    @ExcelProperty("项目名称")
    private String houseName;

    /**
     * 电费年月
     */
    @ExcelProperty("电费年月")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    private LocalDate time;

    /**
     * 补助标准
     */
    @ExcelProperty("补助标准")
    private Double subsidyStandard;

    /**
     * 发电量
     */
    @ExcelProperty("发电量")
    private Double generatingQuantity;

    /**
     * 上网电价
     */
    @ExcelProperty("上网电价")
    private Double networkPrice;

    /**
     * 上网电量
     */
    @ExcelProperty("上网电量")
    private Double networkQuantity;

    /**
     * 应付购电费
     */
    @ExcelProperty("应付购电费")
    private Double payableAmount;

    /**
     * 中央补助金额
     */
    @ExcelProperty("中央补助金额")
    private Double subsidyAmount;

    /**
     * 总应付金额
     */
    @ExcelProperty("总应付金额")
    private Double totalAmount;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String notes;

    /**
     * 该数据创建时间
     */
      @TableField(fill = FieldFill.INSERT)
      @ExcelIgnore
    private LocalDateTime gmtCreate;

    /**
     * 该数据更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
      @ExcelIgnore
    private LocalDateTime gmtModified;


}
