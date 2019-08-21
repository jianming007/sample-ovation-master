package top.zhacker.ddd.identity.domain.role;

import java.util.Collection;

import top.zhacker.ddd.identity.domain.tenant.TenantId;


/**
 * 角色资源-资源库
 * Created by zhacker.
 * Time 2018/6/13 上午11:28
 */
public interface RoleRepo {
  /** 添加校色*/
  void add(Role role);
  /** 根据租户id获取角色列表*/
  Collection<Role> allRoles(TenantId tenantId);
  /** 根据角色对象删除对应角色*/
  void remove(Role role);
  /** 根据租户id和角色名称获取角色*/
  Role roleNamed(TenantId tenantId, String roleName);

}
