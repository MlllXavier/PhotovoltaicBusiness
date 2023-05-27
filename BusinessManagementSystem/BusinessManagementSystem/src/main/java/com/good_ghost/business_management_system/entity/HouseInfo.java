package com.good_ghost.business_management_system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
@TableName("house_info")
public class HouseInfo implements Serializable {

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
     * 户名
     */
    private String houseName;

    /**
     * 地址
     */
    private String houseAddress;

    /**
     * 房屋产权人
     */
    private String houseOwner;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 新建用户原因
     */
    private Integer addReason;

    /**
     * 安装处房屋情况
     */
    private Integer houseType;

    /**
     * 总楼层
     */
    private Integer totalFloor;

    /**
     * 房屋楼层
     */
    private Integer houseFloor;

    /**
     * 安装方式
     */
    private Integer installationType;

    /**
     * 供电电压
     */
    private Integer powerSupply;

    /**
     * 光伏上网容量
     */
    private Double internetCapacity;

    /**
     * 光伏并网时间
     */
    private LocalDateTime connectionTime;

    /**
     * 主要用电设备
     */
    private String poweredDevices;

    /**
     * 发电消纳方式
     */
    private Integer consumptionMode;

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

    /**
     * 预留字段
     */
    private String extField;


}
