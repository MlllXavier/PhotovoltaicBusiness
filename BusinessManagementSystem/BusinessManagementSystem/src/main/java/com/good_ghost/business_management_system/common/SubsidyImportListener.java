package com.good_ghost.business_management_system.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.good_ghost.business_management_system.entity.Subsidy;
import com.good_ghost.business_management_system.service.ISubsidyService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SubsidyImportListener extends AnalysisEventListener<Subsidy> {

    private static final int BATCH_COUNT = 1000;

    List<Subsidy> data = new ArrayList<>();

    ISubsidyService subsidyService;

    public SubsidyImportListener(ISubsidyService service){
        this.subsidyService = service;
    }

    /**
     *  读数据的过程中要做什么,如设置默认值等等
     */
    @Override
    public void invoke(Subsidy subsidy, AnalysisContext analysisContext) {
        log.info("被添加的数据为i{}",subsidy);
        LambdaQueryWrapper<Subsidy> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Subsidy::getHouseId, subsidy.getHouseId()).eq(Subsidy::getTime, subsidy.getTime());
        List<Subsidy> subsidyList = subsidyService.list(wrapper);
        if (subsidyList.size() == 0){
            data.add(subsidy);
        }
        if(data.size()==BATCH_COUNT){
            subsidyService.saveBatch(data);
            data.clear();
        }
    }

    /**
     * 所有数据读完以后要做什么
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        if(!data.isEmpty()){
            subsidyService.saveBatch(data);
            data.clear();
        }
        log.info("恭喜,数据已经导入成功啦!");
    }
}