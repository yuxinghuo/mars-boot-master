package org.mars.base.extra.api;

import org.mars.base.system.api.ISysBaseAPI;
import org.mars.base.system.vo.SysDepartModel;

/**
 * @Description: 扩展的接口
 */
public interface IExtraSysBaseAPI extends ISysBaseAPI {
    /**
     * 通过id查询部门信息
     *
     * @return
     */
    SysDepartModel getSysDepartById(String id);

    /**
     * 获取当前用户的第一个部门信息 <br>
     * 注：<br>
     * 1.此方法必须保证用户登录<br>
     * 2.用户无部门将会抛出异常<br>
     *
     * @return {@link SysDepartModel}
     */
    SysDepartModel getFirstSysDepartOfCurrentUser();

    /**
     * 获取当前登录用户的部门 <br>
     * 注：<br>
     * 1.此方法必须保证用户登录<br>
     * 2.用户无部门将会抛出异常<br>
     *
     * @return {@link SysDepartModel}
     */
    SysDepartModel getDepartByCurrentUserOrgCode();
}
