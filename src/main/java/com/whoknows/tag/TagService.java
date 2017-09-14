package com.whoknows.tag;

import com.whoknows.Constant;
import com.whoknows.comment.CommentService;
import com.whoknows.domain.ActionType;
import com.whoknows.domain.Reply;
import com.whoknows.domain.Tag;
import com.whoknows.domain.TargetType;
import com.whoknows.follow.FollowService;
import com.whoknows.like.LikeService;
import com.whoknows.reply.RelpyService;
import com.whoknows.reply.ReplyDetail;
import com.whoknows.search.Paging;
import com.whoknows.search.TopicResult;
import com.whoknows.topic.TopicDetail;
import com.whoknows.topic.TopicRepository;
import com.whoknows.user.UserDetail;
import com.whoknows.user.UserService;
import com.whoknows.utils.CommonFunction;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final int pageSize = 20;

	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private RelpyService relpyService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private FollowService followService;
	@Autowired
	private CommentService commentService;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean addTag(Tag tag) {
		if (StringUtils.isEmpty(tag.getName())) {
			return false;
		}

		List tagList = getTagList(tag.getName());
		if(tagList!=null && tagList.size()>0){
			//没有同名的才可以添加
			return false;
		}

		tag.setAction(ActionType.active.toString());
		try {
			tagRepository.addTag(tag);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteTag(Tag tag) {
		if (tag.getId() == null) {
			return false;
		}

		try {
			tagRepository.deleteTag(tag);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<TagSelect> getTagList() {
		try {
			return tagRepository.getTagList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Tag> getTagList(String tagName) {
		if (StringUtils.isEmpty(tagName)) {
			return null;
		}

		try {
			return tagRepository.getTagList(tagName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 前台传递-1，那么寻找"最新tag"
	 * @param tagId
	 * @return
	 */
	private Long dealTagId(Long tagId){
		if(tagId==-1L){
			List<Tag> tagList = getTagList(Constant.NEW_TAG_NAME);
			if(tagList==null || tagList.size()==0){
				return tagId;
			}
			return tagList.get(0).getId();
		}
		return tagId;

	}

	public TagHomeRespone getTagHome(Long tagId, Integer page) {
		if (tagId == null || page == null) {
			return null;
		}
		tagId = dealTagId(tagId);

		Tag tag = null;
		try {
			UserDetail user = userService.currentUser();

			TagHomeRespone tagHomeRespone = new TagHomeRespone();
			tag = tagRepository.getTag(tagId);
			tagHomeRespone.setTag(tag);
			tagHomeRespone.setTagFollowCount(followService.followCount(tagId, TargetType.tag));
			if (user != null && user.getId() != null) {
				tagHomeRespone.setCurrentFollowed(followService.isFollowed(user.getId(), tagId, TargetType.tag));
			}

			Paging paging = new Paging();
			paging.setCurrentPage(page);
			paging.setPerPage(pageSize);
			int commentCount = tagRepository.getTopicCountByTag(tagId);
			paging.setTotalPage(commentCount % pageSize == 0 ? commentCount / pageSize : commentCount / pageSize + 1);
			tagHomeRespone.setPaging(paging);

			tagHomeRespone.setTopicResults(tagRepository.getTopicByTag(tagId, page, pageSize).parallelStream().map(topic -> {
				TopicResult topicResult = new TopicResult();
				TopicDetail topicDetail = new TopicDetail();
				topicDetail.setTopic(topic);
				topicDetail.setShortContent(CommonFunction.shortText(topic.getContent()));
				topicDetail.setAuthor(userService.getUser(topic.getUser_id()));
				topicDetail.setFollowCount(followService.followCount(topic.getId(), TargetType.topic));
				if (user != null && user.getId() != null) {
					topicDetail.setCurrentFollowed(followService.isFollowed(user.getId(), topic.getId(), TargetType.topic));
				}
				topicResult.setTopicDetail(topicDetail);

				Reply reply = relpyService.getHotReplyForRopic(topic.getId());
				if (reply != null) {
					ReplyDetail replyDetail = new ReplyDetail();
					replyDetail.setReply(reply);
					replyDetail.setShortContent(CommonFunction.shortText(reply.getContent()));
					replyDetail.setAuthor(userService.getUser(reply.getUser_id()));
					replyDetail.setLikeCount(likeService.likeCount(reply.getId(), TargetType.reply));
					replyDetail.setCommentCount(commentService.commentCount(reply.getId()));
					if (user != null && user.getId() != null) {
						replyDetail.setCurrentLiked(likeService.isLiked(user.getId(), reply.getId(), TargetType.reply));
					}
					topicResult.setReplyDetail(replyDetail);
				}
				return topicResult;
			}).collect(Collectors.toList()));
			return tagHomeRespone;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean addTagRelation(Long topicId, Long tagId) {
		try {
			tagRepository.addTagRelation(topicId, tagId);
			// 也更新"最新"tag
			List<Tag> tagList = this.getTagList(Constant.NEW_TAG_NAME);
			if(tagList!=null && tagList.size()>0){
				Long newTagId = tagList.get(0).getId();
				tagRepository.addTagRelation(topicId, newTagId);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Tag getTagByID(Long tagId) {
		try {
			return tagRepository.getTag(tagId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public  void updateNewTag(){
		List<Tag> tagList = getTagList(Constant.NEW_TAG_NAME);
		if(tagList==null || tagList.size()==0){
			return;
		}
		Long newTagId = tagList.get(0).getId();
		String deleteSql = "delete from tag_topic where tag_id="+newTagId;

		List<Long> newTopics = topicRepository.getNewTopicIds();
		String[] sqlArr = new String[newTopics.size()];
		for (int i = 0; i < newTopics.size(); i++) {
			Long topicId = newTopics.get(i);
			String updateSql = "insert into tag_topic(tag_id,topic_id) values("+newTagId+","+topicId+")";
			sqlArr[i]=updateSql;
		}
		jdbcTemplate.execute(deleteSql);
		jdbcTemplate.batchUpdate(sqlArr);
	}
}
