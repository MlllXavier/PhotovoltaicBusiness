package com.good_ghost.business_management_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.good_ghost.business_management_system.common.Result;
import com.good_ghost.business_management_system.common.ResultCode;
import com.good_ghost.business_management_system.entity.Login;
import com.good_ghost.business_management_system.mapper.LoginMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xavier
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    LoginMapper loginMapper;

    @GetMapping("getPassword")
    public Result getPassword(String username){
        LambdaQueryWrapper<Login> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Login::getUsername, username);
        List<Login> loginList = loginMapper.selectList(wrapper);
        if(loginList.isEmpty()){
            return new Result(ResultCode.FAILED);
        }else{
            return new Result(loginList.get(0).getPassword());
        }
    }

    @PostMapping
    public Result<?> save(@RequestBody Login login){
        LambdaQueryWrapper<Login> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Login::getUsername, login.getUsername());
        List<Login> loginList = loginMapper.selectList(wrapper);
        if(loginList.isEmpty()){
            loginMapper.insert(login);
            return new Result<>("");

        }else{
            return new Result(ResultCode.FAILED, "");
        }
    }
}
