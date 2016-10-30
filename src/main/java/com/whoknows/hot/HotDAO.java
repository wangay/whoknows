package com.whoknows.hot;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HotDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<HotVip> listHotVip(Integer page, int pageSize) {
		return jdbcTemplate.query("select vip.*, user.email, user.phone, user.first_name, user.last_name, user.picture from vip "
				+ "left join user on user.id = vip.user_id "
				+ "order by vip.rank desc "
				+ "limit ? OFFSET ? ",
				ps -> {
					ps.setInt(1, pageSize);
					ps.setInt(2, (page - 1) * pageSize);
				},
				(rs, row) -> {
					HotVip vip = new HotVip();
					if (StringUtils.isEmpty(rs.getString("last_name")) && StringUtils.isEmpty(rs.getString("first_name"))) {
						vip.setName(rs.getString("email"));
					} else {
						vip.setName(rs.getString("last_name") + rs.getString("first_name"));
					}
					vip.setPricture(rs.getString("picture"));
					vip.setFollow(100L);
					vip.setUserID(rs.getLong("user_id"));
					vip.setVipID(rs.getLong("id"));
					return vip;
				});
	}

	public List<HotVip> listHotVip(String key, int page, int pageSize) {
		return jdbcTemplate.query("select vip.*, user.email, user.phone, user.first_name, user.last_name, user.picture from vip "
				+ "left join user on user.id = vip.user_id "
				+ "where email like ? "
				+ "or phone like ? "
				+ "or first_name like ? "
				+ "or last_name like ? "
				+ "order by vip.rank desc "
				+ "limit ? OFFSET ? ",
				ps -> {
					ps.setString(1, "%" + key + "%");
					ps.setString(2, "%" + key + "%");
					ps.setString(3, "%" + key + "%");
					ps.setString(4, "%" + key + "%");
					ps.setInt(5, pageSize);
					ps.setInt(6, (page - 1) * pageSize);
				},
				(rs, row) -> {
					HotVip vip = new HotVip();
					if (StringUtils.isEmpty(rs.getString("last_name")) && StringUtils.isEmpty(rs.getString("first_name"))) {
						vip.setName(rs.getString("email"));
					} else {
						vip.setName(rs.getString("last_name") + rs.getString("first_name"));
					}
					vip.setPricture(rs.getString("picture"));
					vip.setFollow(100L);
					vip.setUserID(rs.getLong("user_id"));
					vip.setVipID(rs.getLong("id"));
					return vip;
				});
	}

	public List<HotTag> listHotTag(Integer page, int pageSize) {
		return jdbcTemplate.query("select * from tag "
				+ "order by rank desc "
				+ "limit ? OFFSET ? ",
				ps -> {
					ps.setInt(1, pageSize);
					ps.setInt(2, (page - 1) * pageSize);
				},
				(rs, row) -> {
					HotTag tag = new HotTag();
					tag.setPicture(null);
					tag.setTagID(rs.getLong("id"));
					tag.setTagName(rs.getNString("name"));
					tag.setFollow(100L);
					return tag;
				});
	}

	public List<HotTag> listHotTag(String key, Integer page, int pageSize) {
		return jdbcTemplate.query("select * from tag "
				+ "where name like ? "
				+ "order by rank desc "
				+ "limit ? OFFSET ? ",
				ps -> {
					ps.setString(1, "%" + key + "%");
					ps.setInt(2, pageSize);
					ps.setInt(3, (page - 1) * pageSize);
				},
				(rs, row) -> {
					HotTag tag = new HotTag();
					tag.setPicture(null);
					tag.setTagID(rs.getLong("id"));
					tag.setTagName(rs.getNString("name"));
					tag.setFollow(100L);
					return tag;
				});
	}
}
