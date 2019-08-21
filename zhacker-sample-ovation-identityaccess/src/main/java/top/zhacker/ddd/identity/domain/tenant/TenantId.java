package top.zhacker.ddd.identity.domain.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 租户id-值对像
 * Created by zhacker.
 * Time 2018/6/11 下午7:04
 */
@Data
@AllArgsConstructor
public class TenantId{
  
  private String id;
  
  protected TenantId() {
    super();
  }
}
