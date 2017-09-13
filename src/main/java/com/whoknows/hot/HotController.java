package com.whoknows.hot;

import com.whoknows.topic.TopicService;
import com.whoknows.vip.VipDetail;
import com.whoknows.tag.TagDetail;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hot")
public class HotController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HotService hotService;

	@Autowired
	private TopicService  topicService;

	@RequestMapping(path = "/vip/{page}", method = RequestMethod.GET)
	public ResponseEntity searchVipyKeyWordOnRank(String keyWord, @PathVariable("page") Integer page) {
		List<VipDetail> vips = null;
		if (keyWord == null) {
			vips = hotService.listHotVip(page);
		} else {
			vips = hotService.listHotVip(keyWord, page);
		}

		if (vips != null) {
			return ResponseEntity.ok(vips);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "/rand/vip", method = RequestMethod.GET)
	public ResponseEntity listRandVip() {
		List<VipDetail> vips = hotService.listRandVip();

		if (vips != null) {
			return ResponseEntity.ok(vips);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "/tag/{page}", method = RequestMethod.GET)
	public ResponseEntity listHotTag(String keyWord, @PathVariable("page") Integer page) {
		List<TagDetail> topics = null;
		if (keyWord == null) {
			topics = hotService.listHotTags(page);
		} else {
			topics = hotService.listHotTags(keyWord, page);
		}
		if (topics != null) {
			setTopicNum(topics,topicService.getTopiccountMap());
			return ResponseEntity.ok(topics);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	private void setTopicNum(List<TagDetail> topics,Map<Long,Long> topicNumMap){

		for (TagDetail tag : topics) {
			Long tagId = tag.getTagID();
			tag.setTotalTopic(topicNumMap.get(tagId)==null?0L:topicNumMap.get(tagId));
		}
	}

	@RequestMapping(path = "/rand/tag", method = RequestMethod.GET)
	public ResponseEntity listRandTag() {
		List<TagDetail> tags = hotService.listRandTags();

		if (tags != null) {
			return ResponseEntity.ok(tags);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(path = "/recommend", method = RequestMethod.GET)
	public ResponseEntity getRecommend() {
		HotRecommend hotIndex = hotService.getRecommend();
		if (hotIndex != null) {
			return ResponseEntity.ok(hotIndex);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
