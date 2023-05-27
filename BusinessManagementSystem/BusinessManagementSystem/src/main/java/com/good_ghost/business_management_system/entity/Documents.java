package com.good_ghost.business_management_system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Documents {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime documentTime;
    private String documentName;
    private String documentNumber;
    private String documentPath;
    private String notes;
}
