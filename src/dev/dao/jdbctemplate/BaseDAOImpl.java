package dev.dao.jdbctemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository(value="baseDAO")
public class BaseDAOImpl implements BaseDAO {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public <T> int update(String sql, final Object[] params, Class<T> tClass) {
		int num = 0;
		try {
			if (params == null || params.length == 0) {
				num = jdbcTemplate.update(sql);
			} else {
				num = jdbcTemplate.update(sql, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						for (int i = 0; i < params.length; i++)
							ps.setObject(i + 1, params[i]);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			num = -1;
		}
		return num;
	}

	// BeanPropertyRowMapper鏄嚜鍔ㄦ槧灏勫疄浣撶被鐨�
	@Override
	public <T> T queryForObject(String sql, Object[] params, Class<T> tClass) {
		if (sql == null || sql.length() <= 0) {
            return null;
        }
        if (params == null || params.length <= 0) {
        	
            return jdbcTemplate.queryForObject(sql, params,  new BeanPropertyRowMapper<T>(tClass));
        }
        return jdbcTemplate.queryForObject(sql, params,new BeanPropertyRowMapper<T>(tClass));
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] params, Class<T> tClass) {
		 List<T> resultList = null;
	        try {
	            if (params != null && params.length > 0)
	                resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<T>(tClass));
	            else
	                resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(tClass));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return resultList;
	}

}
