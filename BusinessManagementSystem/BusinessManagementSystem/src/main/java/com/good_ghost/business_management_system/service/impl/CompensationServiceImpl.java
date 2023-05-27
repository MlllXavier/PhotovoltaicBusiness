package com.good_ghost.business_management_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.good_ghost.business_management_system.entity.Compensation;
import com.good_ghost.business_management_system.entity.Subsidy;
import com.good_ghost.business_management_system.mapper.CompensationMapper;
import com.good_ghost.business_management_system.service.ICompensationService;
import com.good_ghost.business_management_system.service.IHouseInfoService;
import com.good_ghost.business_management_system.service.ISubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xavier
 * @since 2022-07-20
 */
@Service
public class CompensationServiceImpl extends ServiceImpl<CompensationMapper, Compensation> implements ICompensationService {

    @Autowired
    IHouseInfoService houseInfoService;
    @Autowired
    ISubsidyService subsidyService;
    @Resource
    CompensationMapper compensationMapper;

    @Override
    public Compensation getWholeCompensation(Compensation compensation) {
        if (compensation.getSubsidyId() != null){
            compensation.setSubsidyTime(compensation.getSubsidyTime().plusDays(15));
            LambdaQueryWrapper<Compensation> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Compensation::getSubsidyId, compensation.getSubsidyId());
            List<Compensation> compensationList = compensationMapper.selectList(wrapper);
            Compensation compen = compensationList.get(compensationList.size() - 1);
            compensation.setHouseId(compen.getHouseId());
            compensation.setHouseName(compen.getHouseName());
            compensation.setTimeBegin(compen.getTimeBegin());
            compensation.setTimeFinish(compen.getTimeFinish());
            compensation.setErrorFee(compen.getErrorFee());
            compensation.setRightFee(compen.getRightFee());
            compensation.setGeneratingQuantity(compen.getGeneratingQuantity());
            compensation.setCompensationFee(compen.getCompensationFee());
            if(compen.getSubsidyTotalFee() != null){
                compensation.setSubsidyTotalFee(compensation.getSubsidyFee() + compen.getSubsidyTotalFee());
            }else{
                compensation.setSubsidyTotalFee(compensation.getSubsidyFee());
            }
            compensation.setSubsidyRestFee(compensation.getCompensationFee() - compensation.getSubsidyTotalFee());
            compensation.setNotes(compen.getNotes());
            return compensation;
        }
        compensation.setTimeBegin(compensation.getTimeBegin().plusDays(1));
        compensation.setTimeFinish(compensation.getTimeFinish().plusMonths(1));
        compensation.setSubsidyId((compensation.getHouseId() + LocalDate.now()).replace("-", ""));
        List<Subsidy> subsidyList = subsidyService.lambdaQuery().eq(Subsidy::getHouseId, compensation.getHouseId()).list();
        double totalQuantity = 0;
        double totalAmount = 0;
        for(int i = 0; i < subsidyList.size(); i++){
            Subsidy subsidy = subsidyList.get(i);
            if(i == 0){
                compensation.setHouseName(subsidy.getHouseName());
            }
            if(!(subsidy.getTime().isBefore(compensation.getTimeBegin()) || subsidy.getTime().isAfter(compensation.getTimeFinish()))){
                totalQuantity += subsidy.getGeneratingQuantity();
                totalAmount += subsidy.getSubsidyAmount();
            }
        }
        if(totalQuantity != 0){
            compensation.setErrorFee(totalAmount / totalQuantity);
        }
        else {
            compensation.setErrorFee(0.0);
        }
        compensation.setGeneratingQuantity(totalQuantity);
        compensation.setCompensationFee((compensation.getRightFee() - compensation.getErrorFee()) * totalQuantity);
        return compensation;
    }
}
