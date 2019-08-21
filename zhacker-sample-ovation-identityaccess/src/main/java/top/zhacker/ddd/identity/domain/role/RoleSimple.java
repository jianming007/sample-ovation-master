package top.zhacker.ddd.identity.domain.role;

import lombok.Data;
import lombok.experimental.Accessors;
import top.zhacker.ddd.identity.domain.group.Group;
import top.zhacker.ddd.identity.domain.tenant.TenantId;


/**
 * 角色-简单
 * Created by zhacker.
 * Time 2018/7/7 下午9:09
 */
@Data
@Accessors(chain = true)
public class RoleSimple {
  private TenantId tenantId;
  /** 租户名称*/
  private String name;
  /** 租户描述*/
  private String description;
  /** 支持嵌套*/
  private boolean supportsNesting = true;
  /** 租户-组*/
  private Group group;
}
