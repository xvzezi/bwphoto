package DAO;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import model.db.Mark;

/**
 * Mark DAO
 * @author  Xu Zezi
 * @since 2016/7/18
 */
public interface MarkDao 
{
	/**
	 * 创建
	 * @param content
	 * @param item_id
	 * @param target
	 * @param username
	 */
	public int saveObject(String content, Integer item_id, Integer target, String username);

	/**
	 * 删除
	 * @param item_id
	 */
	public void deleteMark(Integer item_id);
	public void deleteMark(Integer id, String username);

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Mark FindMarkById(Integer id);
	public List<Mark> FindMarkByItemId(Integer item_id, Timestamp timestamp, Integer limit);
}
