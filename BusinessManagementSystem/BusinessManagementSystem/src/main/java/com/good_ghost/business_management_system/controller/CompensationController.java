package com.good_ghost.business_management_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.good_ghost.business_management_system.common.Result;
import com.good_ghost.business_management_system.common.ResultCode;
import com.good_ghost.business_management_system.entity.Compensation;
import com.good_ghost.business_management_system.mapper.CompensationMapper;
import com.good_ghost.business_management_system.service.ICompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xavier
 * @since 2022-07-20
 */
@RestController
@RequestMapping("/compensation")
public class CompensationController {

    @Resource
    CompensationMapper compensationMapper;

    @Autowired
    ICompensationService compensationService;

    @PostMapping
    public Result<?> save(@RequestBody Compensation compensation){
        Compensation com = compensationService.getWholeCompensation(compensation);
        if (com == null){
            return new Result<>(ResultCode.FAILED, "");
        }
        compensationMapper.insert(com);
        return new Result<>("");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id){
        compensationMapper.deleteById(id);
        return new Result<>("");
    }

    @PutMapping
    public Result<?> update(@RequestBody Compensation compensation){
        Compensation com = compensationService.getWholeCompensation(compensation);
        if (com == null){
            return new Result<>(ResultCode.FAILED, "");
        }
        compensationMapper.updateById(com);
        return new Result<>("");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "100") Integer pageSize,
                              @RequestParam(defaultValue = "") String searchText,
                              String searchStartTime,
                              String searchEndTime){
        LambdaQueryWrapper<Compensation> wrapper = Wrappers.lambdaQuery();
        if(!searchText.equals("")){
            wrapper.like(Compensation::getHouseId, searchText).or().like(Compensation::getHouseName, searchText);
        }
        if(searchStartTime != null && searchEndTime != null){
            String[] strings = searchStartTime.split("-");
            LocalDate localDateStart = LocalDate.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2].substring(0, 2)));
            strings = searchEndTime.split("-");
            LocalDate localDateEnd = LocalDate.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2].substring(0, 2)));
            wrapper.between(Compensation::getSubsidyTime, localDateStart.plusDays(1), localDateEnd.plusDays(20));
        }
        Page<Compensation> subsidyPage = compensationMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new Result<>(subsidyPage);
    }
}
