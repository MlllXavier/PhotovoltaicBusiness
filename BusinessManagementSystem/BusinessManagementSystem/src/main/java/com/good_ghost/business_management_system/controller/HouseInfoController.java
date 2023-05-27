package com.good_ghost.business_management_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.good_ghost.business_management_system.common.Result;
import com.good_ghost.business_management_system.entity.GridConnect;
import com.good_ghost.business_management_system.entity.HouseInfo;
import com.good_ghost.business_management_system.mapper.GridConnectMapper;
import com.good_ghost.business_management_system.mapper.HouseInfoMapper;
import com.good_ghost.business_management_system.service.IHouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xavier
 * @since 2022-06-09
 */
@RestController
@RequestMapping("/house-info")
public class HouseInfoController {

    @Autowired
    IHouseInfoService houseInfoService;

    @Resource
    HouseInfoMapper houseInfoMapper;

    @Resource
    GridConnectMapper gridConnectMapper;

    @PostMapping
    public Result<?> save(@RequestBody HouseInfo houseInfo){
        houseInfoMapper.insert(houseInfo);
        GridConnect gridConnect = new GridConnect();
        gridConnect.setHouseId(houseInfo.getHouseId());
        gridConnect.setIsComplete(0);
        gridConnect.setProcess1Number("0");
        gridConnect.setProcess7Number("0");
        gridConnectMapper.insert(gridConnect);
        return new Result<>("");
    }

    @PutMapping
    public Result<?> update(@RequestBody HouseInfo houseInfo){
        houseInfoMapper.updateById(houseInfo);
        return new Result<>("");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id){
        LambdaQueryWrapper<GridConnect> wrapper = Wrappers.lambdaQuery();
        HouseInfo houseInfo = houseInfoMapper.selectById(id);
        wrapper.eq(GridConnect::getHouseId, houseInfo.getHouseId());
        gridConnectMapper.delete(wrapper);
        houseInfoMapper.deleteById(id);
        return new Result<>("");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<HouseInfo> wrapper = Wrappers.<HouseInfo>lambdaQuery();
        String Sreason0 = search.equals("")?"":"无";
        String Sreason1 = search.equals("")?"":"无";
        String ShouseType0 = search.equals("")?"":"无";
        String ShouseType1 = search.equals("")?"":"无";
        String ShouseType2 = search.equals("")?"":"无";
        String ShouseType3 = search.equals("")?"":"无";
        String ShouseType4 = search.equals("")?"":"无";
        String ShouseType5 = search.equals("")?"":"无";
        String ShouseType6 = search.equals("")?"":"无";
        String ShouseType7 = search.equals("")?"":"无";
        String ShouseType8 = search.equals("")?"":"无";
        String SinstallationType0 = search.equals("")?"":"无";
        String SinstallationType1 = search.equals("")?"":"无";
        String SpowerSupply0 = search.equals("")?"":"无";
        String SpowerSupply1 = search.equals("")?"":"无";
        String SpowerSupply2 = search.equals("")?"":"无";
        String SconsumptionMode0 = search.equals("")?"":"无";
        String SconsumptionMode1 = search.equals("")?"":"无";
        String SconsumptionMode2 = search.equals("")?"":"无";
        String SconsumptionMode3 = search.equals("")?"":"无";
        if ("全部自用".contains(search)){
            SconsumptionMode0 = "0";
        }
        if ("自发自用".contains(search)){
            SconsumptionMode1 = "1";
        }
        if ("余电上网".contains(search)){
            SconsumptionMode2 = "2";
        }
        if ("全部上网".contains(search)){
            SconsumptionMode3 = "3";
        }
        if ("220V单相".contains(search)){
            SpowerSupply0 = "0";
        }
        if ("380V三相".contains(search)){
            SpowerSupply1 = "1";
        }
        if ("其他".contains(search)){
            SpowerSupply2 = "2";
        }
        if ("新开户".contains(search)){
            Sreason0 = "0";
        }
        if ("原用户补录".contains(search)){
            Sreason1 = "1";
        }
        if ("安装在房屋顶层".contains(search)){
            SinstallationType0 = "0";
        }
        if ("安装在家庭阳台/独立面".contains(search)){
            SinstallationType1 = "1";
        }
        if ("独立".contains(search)){
            ShouseType0 = "0";
        }
        if ("联体".contains(search)){
            ShouseType1 = "1";
        }
        if ("联排".contains(search)){
            ShouseType2 = "2";
        }
        if ("高层".contains(search)){
            ShouseType3 = "3";
        }
        if ("小高层".contains(search)){
            ShouseType4 = "4";
        }
        if ("多层".contains(search)){
            ShouseType5 = "5";
        }
        if ("私房".contains(search)){
            ShouseType6 = "6";
        }
        if ("公租房".contains(search)){
            ShouseType7 = "7";
        }
        if ("其他".contains(search)){
            ShouseType8 = "8";
        }
        if (!search.equals("")) {
            wrapper.like(HouseInfo::getHouseId, search).or()         //户号
                    .like(HouseInfo::getHouseName, search).or()      //户名
                    .like(HouseInfo::getHouseOwner, search).or()     //房屋产权人
                    .like(HouseInfo::getHouseAddress, search).or()   //地址
                    .like(HouseInfo::getContactName, search).or()    //联系人
                    .like(HouseInfo::getContactNumber, search).or()   //联系电话
                    .like(HouseInfo::getAddReason, Sreason0).or()      //新建用户原因-0
                    .like(HouseInfo::getAddReason, Sreason1).or()      //新建用户原因-1
                    .like(HouseInfo::getHouseType, ShouseType0).or()      //安装处房屋情况-0
                    .like(HouseInfo::getHouseType, ShouseType1).or()      //安装处房屋情况-1
                    .like(HouseInfo::getHouseType, ShouseType2).or()      //安装处房屋情况-2
                    .like(HouseInfo::getHouseType, ShouseType3).or()      //安装处房屋情况-3
                    .like(HouseInfo::getHouseType, ShouseType4).or()      //安装处房屋情况-4
                    .like(HouseInfo::getHouseType, ShouseType5).or()      //安装处房屋情况-5
                    .like(HouseInfo::getHouseType, ShouseType6).or()      //安装处房屋情况-6
                    .like(HouseInfo::getHouseType, ShouseType7).or()      //安装处房屋情况-7
                    .like(HouseInfo::getHouseType, ShouseType8).or()      //安装处房屋情况-8
                    .like(HouseInfo::getHouseFloor, search).or()     //居住楼层
                    .like(HouseInfo::getTotalFloor, search).or()     //总楼层
                    .like(HouseInfo::getConsumptionMode, SconsumptionMode0).or()//发电量消耗方式
                    .like(HouseInfo::getConsumptionMode, SconsumptionMode1).or()//发电量消耗方式
                    .like(HouseInfo::getConsumptionMode, SconsumptionMode2).or()//发电量消耗方式
                    .like(HouseInfo::getConsumptionMode, SconsumptionMode3).or()//发电量消耗方式
                    .like(HouseInfo::getInstallationType, SinstallationType0).or()//安装方式
                    .like(HouseInfo::getInstallationType, SinstallationType1).or()//安装方式
                    .like(HouseInfo::getInternetCapacity, search).or()//光伏上网容量
                    .like(HouseInfo::getConnectionTime, search).or() //光伏并网时间
                    .like(HouseInfo::getPoweredDevices, search).or() //主要用电设备
                    .like(HouseInfo::getPowerSupply, SpowerSupply0).or()    //供电电压
                    .like(HouseInfo::getPowerSupply, SpowerSupply1).or()    //供电电压
                    .like(HouseInfo::getPowerSupply, SpowerSupply2).or()    //供电电压
                    .like(HouseInfo::getNotes, search);              //备注
        }
        Page<HouseInfo> houseInfoPage = houseInfoMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new Result<>(houseInfoPage);
    }

}
