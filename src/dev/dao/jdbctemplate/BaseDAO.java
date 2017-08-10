package dev.dao.jdbctemplate;

import java.util.List;

public interface BaseDAO {

	/**
	 * insert/update/delete
	 * 
	 * @param sql
	 * @param params
	 * @param tClass
	 * @return
	 */
	public <T> int update(String sql, Object[] params, Class<T> tClass);

	/**
	 * select one
	 * 
	 * @param sql
	 * @param params
	 * @param tClass
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] params, Class<T> tClass);

	/**
	 * select list
	 * 
	 * @param sql
	 * @param params
	 * @param tClass
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Object[] params, Class<T> tClass);

}
