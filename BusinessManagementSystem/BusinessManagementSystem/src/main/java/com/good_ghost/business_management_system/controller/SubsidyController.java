package com.good_ghost.business_management_system.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.good_ghost.business_management_system.common.*;
import com.good_ghost.business_management_system.entity.Subsidy;
import com.good_ghost.business_management_system.mapper.SubsidyMapper;
import com.good_ghost.business_management_system.service.ISubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xavier
 * @since 2022-06-09
 */
@RestController
@RequestMapping("/subsidy")
public class SubsidyController {

    @Autowired
    ISubsidyService subsidyService;

    @Resource
    SubsidyMapper subsidyMapper;

    @PostMapping
    public Result<?> save(@RequestBody Subsidy subsidy){
        subsidy.setTime(subsidy.getTime().plusDays(15));
        subsidyMapper.insert(subsidy);
        return new Result<>("");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id){
        subsidyMapper.deleteById(id);
        return new Result<>("");
    }

    @PutMapping
    public Result<?> update(@RequestBody Subsidy subsidy){
        subsidy.setTime(subsidy.getTime().plusDays(1));
        subsidyMapper.updateById(subsidy);
        return new Result<>("");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "100") Integer pageSize,
                              @RequestParam(defaultValue = "") String searchText,
                              String searchStartTime,
                              String searchEndTime){
        LambdaQueryWrapper<Subsidy> wrapper = Wrappers.<Subsidy>lambdaQuery();
        if(!searchText.equals("")){
            wrapper.like(Subsidy::getHouseId, searchText).or().like(Subsidy::getHouseName, searchText);
        }
        if(searchStartTime != null && searchEndTime != null){
            String[] strings = searchStartTime.split("-");
            LocalDate localDateStart = LocalDate.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2].substring(0, 2)));
            strings = searchEndTime.split("-");
            LocalDate localDateEnd = LocalDate.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2].substring(0, 2)));
            wrapper.between(Subsidy::getTime, localDateStart.plusDays(1), localDateEnd.plusDays(20));
        }
        Page<Subsidy> subsidyPage = subsidyMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new Result<>(subsidyPage);
    }

    @GetMapping("/getDataFromOneUser")
    public Result getDataFromOneUser(String houseId, int yearIndex){
        double[][] results = new double[5][12];
        LambdaQueryWrapper<Subsidy> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Subsidy::getHouseId, houseId);
        List<Subsidy> subsidyList = subsidyMapper.selectList(wrapper);
        DecimalFormat df = new DecimalFormat("#0.00");
        for(int i = 0; i < subsidyList.size(); i++){
            Subsidy subsidy = subsidyList.get(i);
            if(subsidy.getTime().getYear() == yearIndex){
                int month = subsidy.getTime().getMonthValue();

                results[0][month - 1] += subsidy.getGeneratingQuantity();
                results[1][month - 1] += subsidy.getNetworkQuantity();
                results[2][month - 1] += subsidy.getPayableAmount();
                results[3][month - 1] += subsidy.getSubsidyAmount();
                results[4][month - 1] += subsidy.getTotalAmount();
            }
        }
        return new Result(results);
    }

    @GetMapping("/getYearDataFromAllUser")
    public Result getYearDataFromAllUser(){
        LambdaQueryWrapper<Subsidy> wrapper = Wrappers.lambdaQuery();
        List<Subsidy> subsidyList = subsidyMapper.selectList(wrapper);
        Map<Integer, Double> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#0.00");
        for(int i = 0; i < subsidyList.size(); i++){
            Subsidy subsidy = subsidyList.get(i);
            int year = subsidy.getTime().getYear();
            if (map.containsKey(year)){
                double fees = map.get(year);
                map.replace(year, fees + subsidy.getGeneratingQuantity());
            } else {
                map.put(year, subsidy.getGeneratingQuantity());
            }
        }
        return new Result(map);
    }

    @GetMapping("/getMouthDataFromAllUser")
    public Result getMouthDataFromAllUser(int yearIndex){
        LambdaQueryWrapper<Subsidy> wrapper = Wrappers.lambdaQuery();
        List<Subsidy> subsidyList = subsidyMapper.selectList(wrapper);
        double[][] results = new double[5][12];
        for(int i = 0; i < subsidyList.size(); i++){
            Subsidy subsidy = subsidyList.get(i);
            if(subsidy.getTime().getYear() == yearIndex){
                int month = subsidy.getTime().getMonthValue();
                results[0][month - 1] += subsidy.getGeneratingQuantity();
                results[1][month - 1] += subsidy.getNetworkQuantity();
                results[2][month - 1] += subsidy.getPayableAmount();
                results[3][month - 1] += subsidy.getSubsidyAmount();
                results[4][month - 1] += subsidy.getTotalAmount();
            }
        }
        return new Result(results);
    }

    @PostMapping("import")
    public Result importData(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(),Subsidy.class,new SubsidyImportListener(subsidyService)).sheet().registerConverter(new LocalDateTimeConverter())
                .registerConverter(new LocalDateConverter()).doRead();
        return new Result(ResultCode.SUCCESS);
    }

    @GetMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response){
        List<Subsidy> subsidyList = subsidyService.list();
        EasyExcelUtil.writeExcel(request, response, subsidyList,Subsidy.class,"账户");
    }
}
