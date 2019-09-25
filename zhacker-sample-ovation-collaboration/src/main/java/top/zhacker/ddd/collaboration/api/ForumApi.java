package top.zhacker.ddd.collaboration.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhacker.ddd.collaboration.application.ForumApplicationService;
import top.zhacker.ddd.collaboration.application.ForumQueryService;
import top.zhacker.ddd.collaboration.application.command.ForumAssignModeratorCommand;
import top.zhacker.ddd.collaboration.application.command.ForumChangeDescriptionCommand;
import top.zhacker.ddd.collaboration.application.command.ForumChangeSubjectCommand;
import top.zhacker.ddd.collaboration.application.command.ForumStartCommand;
import top.zhacker.ddd.collaboration.application.vo.ForumCommandResult;
import top.zhacker.ddd.collaboration.application.vo.ForumData;

import java.util.Collection;


/**
 * 论坛api
 * Created by zhacker.
 * Time 2018/7/8 上午11:12
 */
@RestController
@RequestMapping("/v1/forum")
@Slf4j
public class ForumApi {
  
  @Autowired
  private ForumApplicationService forumApplicationService;
  
  @Autowired
  private ForumQueryService forumQueryService;
  /** 开始*/
  @PostMapping("/start")
  public void start(@RequestBody ForumStartCommand command){
    
    ForumCommandResult result = new ForumCommandResult() {
      @Override
      public void resultingForumId(String aForumId) {
        log.info("forumId = {}", aForumId);
      }
      @Override
      public void resultingDiscussionId(String aDiscussionId) {
        throw new UnsupportedOperationException("Should not be reached.");
      }
    };
  
    forumApplicationService.startForum(command, result);
  }
  /** 关闭*/
  @PostMapping("/close")
  public void closeForum(
      String tenantId,
      String forumId) {
    forumApplicationService.closeForum(tenantId, forumId);
  }
  /** 重开*/
  @PostMapping("/reopen")
  public void  reopenForum(
      String tenantId,
      String forumId){
    forumApplicationService.reopenForum(tenantId,forumId);
  }
  /** 所有论坛租户数据*/
  @GetMapping("/all")
  public Collection<ForumData> allForumsDataOfTenant(String aTenantId){
    return forumQueryService.allForumsDataOfTenant(aTenantId);
  }
  /** 详情*/
  @GetMapping("/detail")
  public ForumData forumDataOfId(String aTenantId, String aForumId){
    return forumQueryService.forumDataOfId(aTenantId, aForumId);
  }
  /** 指派主持人论坛*/
  @PostMapping("/assign-moderator")
  public void assignModeratorToForum(@RequestBody ForumAssignModeratorCommand command) {
    forumApplicationService.assignModeratorToForum(command);
  }
  
  /** 改变论坛描述*/
  @PostMapping("change-description")
  public void changeForumDescription(@RequestBody ForumChangeDescriptionCommand command) {
    forumApplicationService.changeForumDescription(command);
  }
  /** 改变论坛主体*/
  @PostMapping("change-subject")
  public void changeForumSubject(@RequestBody ForumChangeSubjectCommand command) {
    forumApplicationService.changeForumSubject(command);
  }
  
}
