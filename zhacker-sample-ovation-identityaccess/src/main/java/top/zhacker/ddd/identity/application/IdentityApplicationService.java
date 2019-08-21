package top.zhacker.ddd.identity.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import top.zhacker.ddd.identity.application.command.ActivateTenantCommand;
import top.zhacker.ddd.identity.application.command.AddGroupToGroupCommand;
import top.zhacker.ddd.identity.application.command.AddUserToGroupCommand;
import top.zhacker.ddd.identity.application.command.AuthenticateUserCommand;
import top.zhacker.ddd.identity.application.command.ChangeContactInfoCommand;
import top.zhacker.ddd.identity.application.command.ChangeEmailAddressCommand;
import top.zhacker.ddd.identity.application.command.ChangePostalAddressCommand;
import top.zhacker.ddd.identity.application.command.ChangePrimaryTelephoneCommand;
import top.zhacker.ddd.identity.application.command.ChangeSecondaryTelephoneCommand;
import top.zhacker.ddd.identity.application.command.ChangeUserPasswordCommand;
import top.zhacker.ddd.identity.application.command.ChangeUserPersonalNameCommand;
import top.zhacker.ddd.identity.application.command.DeactivateTenantCommand;
import top.zhacker.ddd.identity.application.command.DefineUserEnablementCommand;
import top.zhacker.ddd.identity.application.command.OfferRegistrationInvitationCommand;
import top.zhacker.ddd.identity.application.command.ProvisionGroupCommand;
import top.zhacker.ddd.identity.application.command.ProvisionTenantCommand;
import top.zhacker.ddd.identity.application.command.RegisterUserCommand;
import top.zhacker.ddd.identity.application.command.RemoveGroupFromGroupCommand;
import top.zhacker.ddd.identity.application.command.RemoveUserFromGroupCommand;
import top.zhacker.ddd.identity.domain.group.Group;
import top.zhacker.ddd.identity.domain.group.GroupRepo;
import top.zhacker.ddd.identity.domain.tenant.Tenant;
import top.zhacker.ddd.identity.domain.tenant.TenantId;
import top.zhacker.ddd.identity.domain.tenant.TenantProvisionService;
import top.zhacker.ddd.identity.domain.tenant.TenantRepo;
import top.zhacker.ddd.identity.domain.tenant.invitation.RegistrationInvitation;
import top.zhacker.ddd.identity.domain.user.AuthenticationService;
import top.zhacker.ddd.identity.domain.user.Enablement;
import top.zhacker.ddd.identity.domain.user.User;
import top.zhacker.ddd.identity.domain.user.UserDescriptor;
import top.zhacker.ddd.identity.domain.user.UserRepo;
import top.zhacker.ddd.identity.domain.user.person.ContactInformation;
import top.zhacker.ddd.identity.domain.user.person.EmailAddress;
import top.zhacker.ddd.identity.domain.user.person.FullName;
import top.zhacker.ddd.identity.domain.user.person.Person;
import top.zhacker.ddd.identity.domain.user.person.PostalAddress;
import top.zhacker.ddd.identity.domain.user.person.Telephone;


/**
 * 身份申请服务-应用服务(协调领域服务,不做业务处理)
 * Created by zhacker.
 * Time 2018/6/14 下午6:56
 */
@Service
public class IdentityApplicationService {

  @Autowired
  private TenantRepo tenantRepo;

  @Autowired
  private GroupRepo groupRepo;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private TenantProvisionService tenantProvisionService;

  /************* Tenant - 提供租户 **************/
  @Transactional
  public Tenant provisionTenant(ProvisionTenantCommand command){
    // 提供租户
    return this.tenantProvisionService.provisionTenant(
        command.getTenantName(),
        command.getTenantDescription(),
        new FullName(
            command.getAdministorFirstName(),
            command.getAdministorLastName()
        ),
        new EmailAddress(command.getEmailAddress()),
        new PostalAddress(
            command.getAddressStreetAddress(),
            command.getAddressCity(),
            command.getAddressStreetAddress(),
            command.getAddressPostalCode(),
            command.getAddressCountryCode()
        ),
        new Telephone(command.getPrimaryTelephone()),
        new Telephone(command.getSecondaryTelephone())
    );
  }

  public Tenant tenant(String tenantId) {
    return tenantRepo.findByTenantId(new TenantId(tenantId));
  }

  /** 激活租户*/
  @Transactional
  public void activateTenant(ActivateTenantCommand aCommand) {
    // 判断租户是否存在
    Tenant tenant = this.existingTenant(aCommand.getTenantId());
    // 激活
    tenant.activate();
  }

  /** 停用租户*/
  @Transactional
  public void deactivateTenant(DeactivateTenantCommand aCommand) {
    // 判断租户是否存在
    Tenant tenant = this.existingTenant(aCommand.getTenantId());
    // 停用租户
    tenant.deactivate();
  }

  /************* group - 提供组**************/
  @Transactional
  public Group provisionGroup(ProvisionGroupCommand aCommand) {
    // 判断租户是否存在
    Tenant tenant = this.existingTenant(aCommand.getTenantId());
    // 提供组
    Group group =
        tenant.provisionGroup(
            aCommand.getGroupName(),
            aCommand.getDescription());
    // 添加
    this.groupRepo.add(group);

    return group;
  }
  /** 获取组*/
  @Transactional(readOnly=true)
  public Group group(String aTenantId, String aGroupName) {
    Group group =
        this.groupRepo
            .groupNamed(new TenantId(aTenantId), aGroupName);

    return group;
  }
  /** 获取所有组*/
  public Collection<Group> allGroups(String tenantId){
    return groupRepo.allGroups(new TenantId(tenantId));
  }

  /** 向组中添加组*/
  @Transactional
  public void addGroupToGroup(AddGroupToGroupCommand aCommand) {
    // 获取存在的父级组
    Group parentGroup =
        this.existingGroup(
            aCommand.getTenantId(),
            aCommand.getParentGroupName());
    // 获取存在的儿子组
    Group childGroup =
        this.existingGroup(
            aCommand.getTenantId(),
            aCommand.getChildGroupName());
    // 儿子组添加到父组中
    parentGroup.addGroup(childGroup);
  }

  @Transactional
  public void addUserToGroup(AddUserToGroupCommand aCommand) {
    // 获取存在的组
    Group group =
        this.existingGroup(
            aCommand.getTenantId(),
            aCommand.getGroupName());
    // 获取存在的用户
    User user =
        this.existingUser(
            aCommand.getTenantId(),
            aCommand.getUsername());
    // 用户添加到组中
    group.addUser(user);
  }
  /** 是组成员*/
  @Transactional(readOnly=true)
  public boolean isGroupMember(String aTenantId, String aGroupName, String aUsername) {
    // 获取组
    Group group =
        this.existingGroup(
            aTenantId,
            aGroupName);
    // 获取成员
    User user =
        this.existingUser(
            aTenantId,
            aUsername);
    // 半段是不是成员用户
    return group.isMember(user);
  }

  /** 删除组中的组*/
  @Transactional
  public void removeGroupFromGroup(RemoveGroupFromGroupCommand aCommand) {
    // 获取对应父组
    Group parentGroup =
        this.existingGroup(
            aCommand.getTenantId(),
            aCommand.getParentGroupName());
    // 获取对应子组
    Group childGroup =
        this.existingGroup(
            aCommand.getTenantId(),
            aCommand.getChildGroupName());
    // 删除父组的子组
    parentGroup.removeGroup(childGroup);
  }
  /** 从组中删除用户*/
  @Transactional
  public void removeUserFromGroup(RemoveUserFromGroupCommand aCommand) {
    // 获取对应组
    Group group =
        this.existingGroup(
            aCommand.getTenantId(),
            aCommand.getGroupName());
    // 获取对应用户
    User user =
        this.existingUser(
            aCommand.getTenantId(),
            aCommand.getUsername());
    // 从组中删除用户
    group.removeUser(user);

  }

  /************* User  提供注册邀请**************/

  @Transactional
  public RegistrationInvitation offerRegistrationInvitation(OfferRegistrationInvitationCommand command) {
    Tenant tenant = tenantRepo.findByTenantId(new TenantId(command.getTenantId()));
    RegistrationInvitation invitation = tenant.offerRegistrationInvitation(command.getDescription());
    return invitation;
  }

  /**
   * 注册用户
   */
  @Transactional
  public User registerUser(RegisterUserCommand aCommand) {
    // 判断租户
    Tenant tenant = this.existingTenant(aCommand.getTenantId());
    // 注册用户
    User user =
        tenant.registerUser(
            aCommand.getInvitationIdentifier(),
            aCommand.getUsername(),
            aCommand.getPassword(),
            new Enablement(
                aCommand.isEnabled(),
                aCommand.getStartDate(),
                aCommand.getEndDate()),
            new Person(
                new TenantId(aCommand.getTenantId()),
                new FullName(aCommand.getFirstName(), aCommand.getLastName()),
                new ContactInformation(
                    new EmailAddress(aCommand.getEmailAddress()),
                    new PostalAddress(
                        aCommand.getAddressStateProvince(),
                        aCommand.getAddressCity(),
                        aCommand.getAddressStateProvince(),
                        aCommand.getAddressPostalCode(),
                        aCommand.getAddressCountryCode()),
                    new Telephone(aCommand.getPrimaryTelephone()),
                    new Telephone(aCommand.getSecondaryTelephone()))));

    if (user == null) {
      throw new IllegalStateException("User not registered.");
    }
    // 保存用户
    this.userRepo.save(user);
    // 撤回邀请
    tenant.withdrawInvitation(aCommand.getInvitationIdentifier());

    return user;
  }
  /** 验证用户*/
  @Transactional(readOnly=true)
  public UserDescriptor authenticateUser(AuthenticateUserCommand aCommand) {
    // 认证
    UserDescriptor userDescriptor =
        this.authenticationService
            .authenticate(
                new TenantId(aCommand.getTenantId()),
                aCommand.getUsername(),
                aCommand.getPassword());

    return userDescriptor;
  }

  /** 根据用户名称获取用户*/
  @Transactional(readOnly=true)
  public User user(String aTenantId, String aUsername) {
    User user =
        this.userRepo
            .userWithUsername(
                new TenantId(aTenantId),
                aUsername);

    return user;
  }

  /** 获取租户下所有用户*/
  @Transactional(readOnly=true)
  public Collection<User> users(String aTenantId) {

    return this.userRepo.allUsers(new TenantId(aTenantId));

  }

  /** 获取用户描述*/
  @Transactional(readOnly=true)
  public UserDescriptor userDescriptor(
      String aTenantId,
      String aUsername) {

    UserDescriptor userDescriptor = null;
    // 获取用户
    User user = this.user(aTenantId, aUsername);
    // 获取用户描述
    if (user != null) {
      userDescriptor = user.userDescriptor();
    }

    return userDescriptor;
  }

  /** 更改用户联系信息*/
  @Transactional
  public void changeUserContactInformation(ChangeContactInfoCommand aCommand) {
    // 获取存在的用户
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());
    // 更改用户联系人信息
    this.internalChangeUserContactInformation(
        user,
        new ContactInformation(
            new EmailAddress(aCommand.getEmailAddress()),
            new PostalAddress(
                aCommand.getAddressStreetAddress(),
                aCommand.getAddressCity(),
                aCommand.getAddressStateProvince(),
                aCommand.getAddressPostalCode(),
                aCommand.getAddressCountryCode()),
            new Telephone(aCommand.getPrimaryTelephone()),
            new Telephone(aCommand.getSecondaryTelephone())));
  }
  /** 更改用户电子邮件地址*/
  @Transactional
  public void changeUserEmailAddress(ChangeEmailAddressCommand aCommand) {
    // 判断用户
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());
    // 更改用户电子邮件地址
    this.internalChangeUserContactInformation(
        user,
        user.getPerson()
            .contactInformation()
            .changeEmailAddress(new EmailAddress(aCommand.getEmailAddress())));
  }
  /** 更改用户邮政地址*/
  @Transactional
  public void changeUserPostalAddress(ChangePostalAddressCommand aCommand) {
    // 判断用户
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());
    // 更改用户邮政地址
    this.internalChangeUserContactInformation(
        user,
        user.getPerson()
            .contactInformation()
            .changePostalAddress(
                new PostalAddress(
                    aCommand.getAddressStreetAddress(),
                    aCommand.getAddressCity(),
                    aCommand.getAddressStateProvince(),
                    aCommand.getAddressPostalCode(),
                    aCommand.getAddressCountryCode())));
  }
  /** 更改用户主电话*/
  @Transactional
  public void changeUserPrimaryTelephone(ChangePrimaryTelephoneCommand aCommand) {
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());
    // 更改用户主电话
    this.internalChangeUserContactInformation(
        user,
        user.getPerson()
            .contactInformation()
            .changePrimaryTelephone(new Telephone(aCommand.getTelephone())));
  }
  /** 更改次要电话*/
  @Transactional
  public void changeUserSecondaryTelephone(ChangeSecondaryTelephoneCommand aCommand) {
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

    this.internalChangeUserContactInformation(
        user,
        user.getPerson()
            .contactInformation()
            .changeSecondaryTelephone(new Telephone(aCommand.getTelephone())));
  }

  /**更改用户密码*/
  @Transactional
  public void changeUserPassword(ChangeUserPasswordCommand aCommand) {
    // 判断用户
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());
    // 更改用户密码
    user.changePassword(aCommand.getCurrentPassword(), aCommand.getChangedPassword());
  }
  /**更改用户个人姓名*/
  @Transactional
  public void changeUserPersonalName(ChangeUserPersonalNameCommand aCommand) {
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

    user.getPerson().changeName(new FullName(aCommand.getFirstName(), aCommand.getLastName()));
  }
  /** 定义用户启用*/
  @Transactional
  public void defineUserEnablement(DefineUserEnablementCommand aCommand) {
    User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

    user.defineEnablement(
        new Enablement(
            aCommand.isEnabled(),
            aCommand.getStartDate(),
            aCommand.getEndDate()));
  }
  /** 内部更改用户联系信息*/
  private void internalChangeUserContactInformation(
      User aUser,
      ContactInformation aContactInformation) {

    if (aUser == null) {
      throw new IllegalArgumentException("User must exist.");
    }
    // 更改用户联系信息
    aUser.getPerson().changeContactInformation(aContactInformation);
  }


  /** 判断租户是否存在*/
  private Tenant existingTenant(String aTenantId) {
    Tenant tenant = this.tenant(aTenantId);

    if (tenant == null) {
      throw new IllegalArgumentException(
          "Tenant does not exist for: " + aTenantId);
    }
    return tenant;
  }
  /** 获取存在的组,不存在会抛出异常*/
  private Group existingGroup(String aTenantId, String aGroupName) {
    // 按条件获取组
    Group group = this.group(aTenantId, aGroupName);

    if (group == null) {
      throw new IllegalArgumentException(
          "Group does not exist for: "
              + aTenantId + " and: " + aGroupName);
    }

    return group;
  }

  /** 获取存在的用户,不存在会抛出异常*/
  private User existingUser(String aTenantId, String aUsername) {
    User user = this.user(aTenantId, aUsername);

    if (user == null) {
      throw new IllegalArgumentException(
          "User does not exist for: "
              + aTenantId + " and " + aUsername);
    }

    return user;
  }

}
