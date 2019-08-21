package top.zhacker.ddd.identity.domain.role.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
//import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.core.model.BaseDomainEvent;
import top.zhacker.ddd.identity.domain.tenant.TenantId;


/**
 * 组分配给角色事件
 * Created by zhacker.
 * Time 2018/6/30 下午5:38
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GroupAssignedToRole extends BaseDomainEvent {
  private TenantId tenantId;
  private String roleName;
  private String groupName;
}
