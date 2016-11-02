package com.whoknows.like;

import com.whoknows.domain.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/like")
public class LikeController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LikeService likeService;

	@RequestMapping(path = "/reply/{userId}/{replyId}", method = RequestMethod.POST)
	public ResponseEntity likeReply(@PathVariable("userId") Long userId, @PathVariable("replyId") Long replyId) {
		log.info("{} like reply {}.", userId, replyId);
		if (likeService.like(userId, replyId, TargetType.reply)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
