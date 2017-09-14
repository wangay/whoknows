package com.whoknows;

import com.whoknows.domain.Tag;
import com.whoknows.domain.Topic;
import com.whoknows.tag.TagService;
import com.whoknows.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nobody on 2017/9/14.
 * 维护一个tag,名字叫最新， 里面放置所有topic中最新的200条
 * 24个小时更新一次。
 * 新增topic时，也更新最新tag的信息
 */
@Component
public class NewTopicSchedule {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //@Scheduled(fixedRate = 5000)
    //秒，分 ，时 凌晨5点  0 0 12 ? * WED 表示每个星期三中午12点
    //http://www.cnblogs.com/liuyitian/p/4108391.html
    @Scheduled(cron = "0 0 05 ? * *")
    //@Scheduled(cron = "0 49 18 ? * *")
    public  void updateNewTag(){
        tagService.updateNewTag();
    }
}
