package service;

import java.sql.Timestamp;
import java.util.List;

import model.db.Mark;

/**
 * Service Of Marks
 * @since 2016/7/11
 * @author Xu Zezi
 * @version
 *      0   Basic Impl.
 *      1   Marks getting logic improved
 */
public interface MarkService
{
	/**
     * 通过各种情况获取评论
     * @param item_id
     * @param mark_id
     * @param username
     * @param timestamp
     * @param pick_size
     * @return 评论
     *      1   timestamp   决定评论的起始时间，获取pick_size个。为空则获取最新
     *      2   要求resource的状态为公开可见的
     */
    public List<Mark> getMarks(Integer item_id, Integer mark_id, String username, Timestamp timestamp, int  pick_size);

	/**
	 * 创建一个评论
     * @param content
     * @param item_id
     * @param target
     * @param username
     * @return id
     *      1   target       如果为空则为独立评论，不为空则为评论的评论
     *      2   要求resource的状态为公开可见的
     */
    public int createMark(String content, Integer item_id, Integer target, String username);

	/**
	 * 删除一个评论
     * @param id
     * @param username
     * @return 信息
     *      1   要求对该条评论的所有权
     */
    public String deleteMark(Integer id, String username);
}
