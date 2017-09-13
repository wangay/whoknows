package com.whoknows.topic;

import com.whoknows.domain.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TopicRepository {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Long createTopic(Topic topic) {
		jdbcTemplate.update("insert into topic(user_id, title, content, action) values (?, ?, ?, ?)",
				ps -> {
					ps.setLong(1, topic.getUser_id());
					ps.setString(2, topic.getTitle());
					ps.setString(3, topic.getContent());
					ps.setString(4, topic.getAction());
				});

		return jdbcTemplate.query("select id from topic where user_id = ? order by id desc limit 1",
				ps -> ps.setLong(1, topic.getUser_id()),
				(rs, row) -> {
					return rs.getLong("id");
				}).stream().findAny().orElse(null);
	}

	public void updateTopic(Topic topic) {
		jdbcTemplate.update("update topic set title = ? , content = ? where id = ? ",
				ps -> {
					ps.setString(1, topic.getTitle());
					ps.setString(2, topic.getContent());
					ps.setLong(3, topic.getId());
				});
	}

	public void deleteTopic(Long id) {
		jdbcTemplate.update("delete from topic where id = ? ", ps -> ps.setLong(1, id));
	}

	public Topic getTopic(Long id) {
		return jdbcTemplate.query("select * from topic where id = ? limit 1",
				ps -> ps.setLong(1, id),
				(rs, row) -> {
					Topic topic = new Topic();
					topic.setId(rs.getLong("id"));
					topic.setUser_id(rs.getLong("user_id"));
					topic.setAction(rs.getString("action"));
					topic.setTitle(rs.getString("title"));
					topic.setContent(rs.getString("content"));
					topic.setRank(rs.getLong("rank"));
					topic.setCreate_time(rs.getTimestamp("create_time"));
					topic.setUpdate_time(rs.getTimestamp("update_time"));
					return topic;
				}).stream().findAny().orElse(null);
	}

	/***
	 * 每个tag有多少topic
	 * @return Map
	 */
	public Map<Long,Long>  getTopicNumMap(){
		String sql = "select tag_id,count(tag_id) as  topicNum  from tag_topic group by tag_id ";
		List list = jdbcTemplate.queryForList(sql);
		Map<Long,Long> topicNumMap = new HashMap<Long,Long>();
		for (Object o : list) {
			Map map = (Map)o;
			Long tagId = (Long)map.get("tag_id");
			Long topicNum = (Long) map.get("topicNum");
			topicNumMap.put(tagId,topicNum);
		}
		return topicNumMap;
	}
}
