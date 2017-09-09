package com.whoknows.tag;

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
import com.whoknows.user.UserDetail;
import com.whoknows.user.UserService;
import com.whoknows.utils.CommonFunction;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public TagHomeRespone getTagHome(Long tagId, Integer page) {
		if (tagId == null || page == null) {
			return null;
		}

		try {
			UserDetail user = userService.currentUser();

			TagHomeRespone tagHomeRespone = new TagHomeRespone();
			tagHomeRespone.setTag(tagRepository.getTag(tagId));
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
}
