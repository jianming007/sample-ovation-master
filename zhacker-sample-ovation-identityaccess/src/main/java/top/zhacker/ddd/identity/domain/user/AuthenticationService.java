package top.zhacker.ddd.identity.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.zhacker.core.model.AssertionConcern;
import top.zhacker.ddd.identity.domain.tenant.Tenant;
import top.zhacker.ddd.identity.domain.tenant.TenantId;
import top.zhacker.ddd.identity.domain.tenant.TenantRepo;


/**
 * 验证服务-领域服务
 * Created by zhacker.
 * Time 2018/6/30 下午10:32
 */
@Component
public class AuthenticationService extends AssertionConcern {
  
  private EncryptionService encryptionService;
  private TenantRepo tenantRepo;
  private UserRepo userRepo;
  
  @Autowired
  public AuthenticationService(EncryptionService encryptionService, TenantRepo tenantRepo, UserRepo userRepo) {
    this.encryptionService = encryptionService;
    this.tenantRepo = tenantRepo;
    this.userRepo = userRepo;
  }
  /** 认证*/
  public UserDescriptor authenticate(
      TenantId aTenantId,
      String aUsername,
      String aPassword) {
    
    this.assertArgumentNotNull(aTenantId, "TenantId must not be null.");
    this.assertArgumentNotEmpty(aUsername, "Username must be provided.");
    this.assertArgumentNotEmpty(aPassword, "Password must be provided.");
    // 获取null的用户描述
    UserDescriptor userDescriptor = UserDescriptor.nullDescriptorInstance();
    // 查询对应租户
    Tenant tenant = this.tenantRepo.findByTenantId(aTenantId);
    // 判断租户 和 激活状态
    if (tenant != null && tenant.isActive()) {
      // 加密密码
      String encryptedPassword = this.encryptionService.encryptedValue(aPassword);
      // 用户来自真实认证
      User user =
          this.userRepo
              .userFromAuthenticCredentials(
                  aTenantId,
                  aUsername,
                  encryptedPassword);
      
      if (user != null && user.isEnabled()) {
        userDescriptor = user.userDescriptor();
      }
    }
    
    return userDescriptor;
  }
  
}
