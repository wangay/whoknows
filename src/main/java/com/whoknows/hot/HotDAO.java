package com.whoknows.hot;

import com.whoknows.vip.VipDetail;
import com.whoknows.tag.TagDetail;
import com.whoknows.domain.RoleType;
import com.whoknows.utils.CommonFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<VipDetail> listHotVip(Integer page, int pageSize) {
		return jdbcTemplate.query("select user.* from user "
				+ "left join user_role on user_role.user_id = user.id "
				+ "where user_role.role_id = ( select id from role where role = '" + RoleType.SITE_VIP.toString() + "' limit 1 ) "
				+ "order by rank desc "
				+ "limit ? OFFSET ? ",
				ps -> {
					ps.setInt(1, pageSize);
					ps.setInt(2, (page - 1) * pageSize);
				},
				(rs, row) -> {
					VipDetail vip = new VipDetail();
					vip.setName(CommonFunction.getUserName(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email")));
					vip.setPicture(rs.getString("picture"));
					vip.setUserID(rs.getLong("id"));
					return vip;
				});
	}

	public List<VipDetail> listRandVip(int pageSize) {
		return jdbcTemplate.query("select user.* from user "
				+ "left join user_role on user_role.user_id = user.id "
				+ "where user_role.role_id = ( select id from role where role = '" + RoleType.SITE_VIP.toString() + "' limit 1 ) "
				+ "order by rand() "
				+ "limit ? ",
				ps -> {
					ps.setInt(1, pageSize);
				},
				(rs, row) -> {
					VipDetail vip = new VipDetail();
					vip.setName(CommonFunction.getUserName(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email")));
					vip.setPicture(rs.getString("picture"));
					vip.setUserID(rs.getLong("id"));
					return vip;
				});
	}

	public List<VipDetail> listHotVip(String key, int page, int pageSize) {
		return jdbcTemplate.query("select user.* from user "
				+ "left join user_role on user_role.user_id = user.id "
				+ "where user_role.role_id = ( select id from role where role = '" + RoleType.SITE_VIP.toString() + "' limit 1 ) "
				+ "and ( email like ? "
				+ "or phone like ? "
				+ "or first_name like ? "
				+ "or last_name like ? ) "
				+ "order by rank desc "
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
					VipDetail vip = new VipDetail();
					vip.setName(CommonFunction.getUserName(rs.getString("first_name"), rs.getString("last_name"), rs.getString("email")));
					vip.setPicture(rs.getString("picture"));
					vip.setUserID(rs.getLong("id"));
					return vip;
				});
	}

	public List<TagDetail> listHotTag(Integer page, int pageSize) {
		return jdbcTemplate.query("select * from tag "
				+ "order by rank desc "
				+ "limit ? OFFSET ? ",
				ps -> {
					ps.setInt(1, pageSize);
					ps.setInt(2, (page - 1) * pageSize);
				},
				(rs, row) -> {
					TagDetail tag = new TagDetail();
					tag.setTagName(rs.getNString("name"));
					tag.setPicture(getTagPicture(tag.getTagName()));
					tag.setTagID(rs.getLong("id"));
					return tag;
				});
	}

	private static final Map<String,String> TAG_PICTURE_MAP  = new HashMap<>();
	{

		TAG_PICTURE_MAP.put("其他","qita");
		TAG_PICTURE_MAP.put("技术","jishu");
		TAG_PICTURE_MAP.put("安全与隐私","anquan");
		TAG_PICTURE_MAP.put("暗网与深网","anwang");
		TAG_PICTURE_MAP.put("买卖与交易" ,"maimai");
		TAG_PICTURE_MAP.put("钱" ,"qian");
		TAG_PICTURE_MAP.put("雇佣" ,"guyong");
		TAG_PICTURE_MAP.put("蘑菇大麻lsd" ,"dama");
		TAG_PICTURE_MAP.put("国际" ,"guoji");
		TAG_PICTURE_MAP.put("法律" ,"falv");
		TAG_PICTURE_MAP.put("性" ,"sex");
		TAG_PICTURE_MAP.put("悲伤时刻" ,"beishang");
		TAG_PICTURE_MAP.put("食物" ,"shiwu");
		TAG_PICTURE_MAP.put("最新" ,"zuixin");
	}
	private String getTagPicture(String tagName){
		return "/images/tag/"+TAG_PICTURE_MAP.get(tagName)+".png";
	}

	public List<TagDetail> listRandTag(int pageSize) {
		return jdbcTemplate.query("select * from tag "
				+ "order by rand() "
				+ "limit ? ",
				ps -> {
					ps.setInt(1, pageSize);
				},
				(rs, row) -> {
					TagDetail tag = new TagDetail();
					tag.setPicture(null);
					tag.setTagID(rs.getLong("id"));
					tag.setTagName(rs.getNString("name"));
					return tag;
				});
	}

	public List<TagDetail> listHotTag(String key, Integer page, int pageSize) {
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
					TagDetail tag = new TagDetail();
					tag.setPicture(null);
					tag.setTagID(rs.getLong("id"));
					tag.setTagName(rs.getNString("name"));
					return tag;
				});
	}
}
