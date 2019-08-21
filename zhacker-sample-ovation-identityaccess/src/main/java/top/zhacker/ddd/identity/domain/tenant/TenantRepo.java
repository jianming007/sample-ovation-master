package top.zhacker.ddd.identity.domain.tenant;

import java.util.List;

/**
 * 租户资源-资源库
 * Created by zhacker.
 * Time 2018/6/11 下午7:04
 */
public interface TenantRepo {
  /** 添加租户*/
  void add(Tenant tenant);
  /** 根据租户id查询租户*/
  Tenant findByTenantId(TenantId tenantId);
  /** 下一个身份*/
  TenantId nextIdentity();
  /** 获取所有租户*/
  List<Tenant> findAll();

}
