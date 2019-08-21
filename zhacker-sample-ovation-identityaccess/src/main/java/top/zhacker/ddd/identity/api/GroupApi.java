package top.zhacker.ddd.identity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import top.zhacker.ddd.identity.application.IdentityApplicationService;
import top.zhacker.ddd.identity.application.command.AddGroupToGroupCommand;
import top.zhacker.ddd.identity.application.command.AddUserToGroupCommand;
import top.zhacker.ddd.identity.application.command.ProvisionGroupCommand;
import top.zhacker.ddd.identity.application.command.RemoveGroupFromGroupCommand;
import top.zhacker.ddd.identity.application.command.RemoveUserFromGroupCommand;
import top.zhacker.ddd.identity.domain.group.Group;


/**
 * 组api
 * Created by zhacker.
 * Time 2018/7/6 下午10:23
 */
@RestController
@RequestMapping("/v1/groups")
public class GroupApi {
  
  @Autowired
  private IdentityApplicationService applicationService;

  /** 规定组*/
  @PostMapping("/provision")
  public Group provisionGroup(@RequestBody ProvisionGroupCommand aCommand) {
    return applicationService.provisionGroup(aCommand);
  }
  /** 添加组到组中*/
  @PostMapping("/add-group")
  public void addGroupToGroup(@RequestBody AddGroupToGroupCommand aCommand) {
    applicationService.addGroupToGroup(aCommand);
  }
  /** 添加用户到组中*/
  @PostMapping("/add-user")
  public void addUserToGroup(@RequestBody AddUserToGroupCommand aCommand) {
    applicationService.addUserToGroup(aCommand);
  }
  /** 从组中删除组*/
  @PostMapping("/remove-group")
  public void removeGroupFromGroup(@RequestBody RemoveGroupFromGroupCommand aCommand) {
    applicationService.removeGroupFromGroup(aCommand);
  }
  /** 从组中删除用户*/
  @PostMapping("/remove-user")
  public void removeUserFromGroup(RemoveUserFromGroupCommand aCommand) {
    applicationService.removeUserFromGroup(aCommand);
  }
  /** 获取单个组*/
  @GetMapping("detail")
  public Group group(String tenantId, String groupName){
    return applicationService.group(tenantId, groupName);
  }
  /** 获取所有组*/
  @GetMapping("/list")
  public Collection<Group> allGroups(String tenantId){
    return applicationService.allGroups(tenantId);
  }
}
