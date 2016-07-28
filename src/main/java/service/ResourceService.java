package service;

import model.BasicInfo;
import model.db.Item;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * 有关基础resource的服务
 * @author Xu Zezi
 * @since 2016/7/5.
 * @version
 *      0   Basic Design
 *      1   Fetch resource by timestamp   2016/7/19
 *      1.1 auth improved                 2016/7/19
 *      1.2 auth change function          2016/7/19
 *      2.0 auth control and resource added 2016/7/28
 */
public interface ResourceService
{
	/**
     * 获取全部资源或者一个资源
     * @param name
     * @param resource_id
     * @return 列表
     *  - resource_id 不为空
     *      通过resource_id与用户进行匹配，成功返回所有的资源，
     *  如果不匹配，则查看权限，权限不为public则返回空。
     *  - resource_id 为空
     *      通过name获取该用户的所有资源。
     *
     */
    public List<Item> getResources(String name, int resource_id);

	/**
	 * 创建一个空资源，以存放新的信息
     * @param name
     * @param basicInfo
     * @return 新资源
     */
    public Item createResource(String name, BasicInfo basicInfo);

	/**
	 * 删除一个资源
     * @param name
     * @param resource_id
     * @return 进度信息
     *      如果用户不拥有该资源，则资源不可被删除
     */
    public boolean deleteResource(String name, int resource_id);

	/**
     * 获取最新的资源
     * @param timestamp
     * @param amount
     * @return list
     * - timestamp为空
     *      返回最近的amount条
     * - timestamp 不为空
     *      返回timestamp之前的amount条
     */
    public List<Item> getLatestResource(Timestamp timestamp, int amount);

	/**
     * 改变资源的可见度
     * @param name
     * @param resource_id
     * @return old status
     *      拥有者可以更改自己的资源的权限，以确认展示与否
     */
    public char changeToPublic(String name, int resource_id);
    public char changeToPrivate(String name, int resource_id);

	/**
	 * 查看好友的最近信息
	 * @param name
	 * @param timestamp
	 * @param amount
	 * @return 朋友最近的信息
	 *          返回朋友当中最近的、以时间戳为起点的、不超过amount个数的resources
	 */
	public List<Item> getFriendAndLatest(String name, Timestamp timestamp, int amount);

	/**
	 * 获取某个人的信息
	 * @param tarname
	 * @param username
	 * @param timestamp
	 * @param amount
	 * @return 某个人的信息
	 *      查询者为自己的情况下——获取全部信息，按照给与的timestamp去fetch
	 *      查询他人的情况下——只能获取状态为public 的部分
	 */
	public List<Item> getPersonalResource(String tarname, String username, Timestamp timestamp, int amount);
}
