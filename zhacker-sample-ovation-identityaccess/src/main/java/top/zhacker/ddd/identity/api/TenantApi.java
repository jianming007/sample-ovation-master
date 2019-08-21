package top.zhacker.ddd.identity.api;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zhacker.ddd.identity.application.IdentityApplicationService;
import top.zhacker.ddd.identity.application.command.ActivateTenantCommand;
import top.zhacker.ddd.identity.application.command.DeactivateTenantCommand;
import top.zhacker.ddd.identity.application.command.OfferRegistrationInvitationCommand;
import top.zhacker.ddd.identity.application.command.ProvisionTenantCommand;
import top.zhacker.ddd.identity.application.request.TenantProvisionRequest;
import top.zhacker.boot.registry.DomainRegistry;
import top.zhacker.ddd.identity.domain.tenant.Tenant;
import top.zhacker.ddd.identity.domain.tenant.TenantRepo;
import top.zhacker.ddd.identity.domain.tenant.invitation.RegistrationInvitation;

import java.util.List;


/**
 * 客户api-控制层
 * Created by zhacker.
 * Time 2018/6/13 上午10:07
 */
@RestController
@RequestMapping("/v1/tenants")
public class TenantApi {
  
  @Autowired
  private IdentityApplicationService identityApplicationService;

  @Autowired
  private TenantRepo tenantRepo;

  /** 获取所有-租户*/
  @GetMapping("/list")
  public List<Tenant> list(){
    // 这里通过资源层直接获取
    return tenantRepo.findAll();
  }
  /** 下一个身份*/
  @GetMapping("/nextId")
  public String nextId(){
    return DomainRegistry.repo(TenantRepo.class).nextIdentity().getId();
  }
  /** 根据租户id获取-租户*/
  @GetMapping("/{tenantId}")
  public Tenant findTenantById(@PathVariable("tenantId") String tenantId){
    return identityApplicationService.tenant(tenantId);
  }
  /** 规定-租户*/
  @PostMapping("/provision")
  public Tenant provision(@RequestBody ProvisionTenantCommand request){
    return identityApplicationService.provisionTenant(request);
  }
  /** 激活-租户*/
  @PostMapping("/activate")
  public void activateTenant(@RequestBody ActivateTenantCommand aCommand){
    identityApplicationService.activateTenant(aCommand);
  }
  /** 停用-租户*/
  @PostMapping("deactivate")
  public void deactivateTenant(@RequestBody DeactivateTenantCommand aCommand){
    identityApplicationService.deactivateTenant(aCommand);
  }
  /** 提供注册邀请*/
  @PostMapping("invitation")
  public RegistrationInvitation offerRegistrationInvitation(@RequestBody OfferRegistrationInvitationCommand command) {
    return identityApplicationService.offerRegistrationInvitation(command);
  }
  
}
