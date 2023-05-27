package com.good_ghost.business_management_system.service.impl;

import com.good_ghost.business_management_system.entity.Login;
import com.good_ghost.business_management_system.mapper.LoginMapper;
import com.good_ghost.business_management_system.service.ILoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xavier
 * @since 2022-07-20
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

}
