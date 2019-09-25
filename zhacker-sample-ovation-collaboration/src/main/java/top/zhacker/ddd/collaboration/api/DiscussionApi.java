package top.zhacker.ddd.collaboration.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.zhacker.ddd.collaboration.application.DiscussionApplicationService;
import top.zhacker.ddd.collaboration.application.DiscussionQueryService;
import top.zhacker.ddd.collaboration.application.ForumApplicationService;
import top.zhacker.ddd.collaboration.application.command.DiscussionStartCommand;
import top.zhacker.ddd.collaboration.application.vo.DiscussionData;
import top.zhacker.ddd.collaboration.application.vo.DiscussionPostsData;

import java.util.Collection;


/**
 * 讨论api
 * Created by zhacker.
 * Time 2018/7/14 下午4:26
 */
@RestController
@RequestMapping("/v1/discussion")
public class DiscussionApi {
  
  @Autowired
  private DiscussionQueryService discussionQueryService;
  
  @Autowired
  private DiscussionApplicationService discussionApplicationService;
  
  @Autowired
  private ForumApplicationService forumApplicationService;
  /** 论坛的所有讨论数据*/
  @GetMapping("all")
  public Collection<DiscussionData> allDiscussionsDataOfForum(String aTenantId, String aForumId) {
    return discussionQueryService.allDiscussionsDataOfForum(aTenantId, aForumId);
  }
  /** 详情*/
  @GetMapping("detail")
  public DiscussionData discussionDataOfId(String aTenantId, String aDiscussionId) {
    return discussionQueryService.discussionDataOfId(aTenantId,aDiscussionId);
  }
  /** 讨论Id的发布数据*/
  @GetMapping("detail-with-posts")
  public DiscussionPostsData discussionPostsDataOfId(String aTenantId, String aDiscussionId) {
    return discussionQueryService.discussionPostsDataOfId(aTenantId,aDiscussionId);
  }
  /** 关闭讨论*/
  @PostMapping("close")
  public void closeDiscussion(String aTenantId, String aDiscussionId) {
    discussionApplicationService.closeDiscussion(aTenantId,aDiscussionId);
  }
  /** 重开讨论*/
  @PostMapping("reopen")
  public void reopenDiscussion(String aTenantId, String aDiscussionId) {
    discussionApplicationService.reopenDiscussion(aTenantId, aDiscussionId);
  }
  /** 开始*/
  @PostMapping("start")
  public void start(@RequestBody DiscussionStartCommand command){
    forumApplicationService.startDiscussion(command);
  }
  
}
