package top.zhacker.ddd.identity.domain.role.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.ddd.identity.domain.tenant.TenantId;


/**
 * 用户从角色取消分配事件
 * Created by zhacker.
 * Time 2018/6/30 下午5:38
 */
@Getter
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserUnassignedFromRole extends BaseDomainEvent {
  
  private TenantId tenantId;
  private String roleName;
  private String username;
  
}
