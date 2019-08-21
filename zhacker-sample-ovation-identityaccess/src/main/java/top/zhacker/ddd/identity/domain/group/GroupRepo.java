package top.zhacker.ddd.identity.domain.group;

import java.util.Collection;

import top.zhacker.ddd.identity.domain.tenant.TenantId;


/**
 * 组资源-资源库
 * Created by zhacker.
 * Time 2018/6/30 下午1:45
 */
public interface GroupRepo {
  /** 添加*/
  void add(Group group);
  /** 查询所有*/
  Collection<Group> allGroups(TenantId tenantId);
  /** 根据租户id,名称获取组*/
  Group groupNamed(TenantId tenantId, String name);
  /** 删除组*/
  void remove(Group group);
}
