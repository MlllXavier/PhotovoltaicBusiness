package com.good_ghost.business_management_system.common;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class EasyExcelUtil {
    public static <T> void writeExcel(HttpServletRequest request, HttpServletResponse response, List<T> data, Class tClass, String name){
        try {
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            name = name+dateStr;
            //防止下载时中文乱码
            name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //添加这个是防止前端拿不到Content-disposition
            response.setHeader("Content-Disposition", "attachment;filename=" + name+".xlsx");
            response.setHeader("Access-Control-Expose-Headers","Content-disposition");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), tClass).autoCloseStream(Boolean.FALSE).sheet(name).registerConverter(new LocalDateConverter()).registerConverter(new LocalDateTimeConverter())
                    .doWrite(data);
        } catch (Exception e) {
            log.error("文件导出失败,错误信息{}",e);
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

        }
    }
}