package com.good_ghost.business_management_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.good_ghost.business_management_system.entity.Compensation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xavier
 * @since 2022-07-20
 */
public interface ICompensationService extends IService<Compensation> {

    Compensation getWholeCompensation(Compensation compensation);

}
