package com.good_ghost.business_management_system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;

public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql:///photovoltaic_business", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("Xavier") // 设置作者
                            .fileOverride()
                            .outputDir("C:\\Users\\Administrator\\Documents\\program\\BusinessManagementSystem\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.good_ghost.business_management_system"); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.controllerBuilder().enableRestStyle().enableHyphenStyle()
                            .entityBuilder().enableLombok().addTableFills(
                            new Column("gmt_create", FieldFill.INSERT)).addTableFills(
                            new Column("gmt_modified", FieldFill.INSERT_UPDATE));
                })
                .execute();
    }
}
