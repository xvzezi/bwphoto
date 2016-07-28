package DAO;

import java.sql.Timestamp;
import java.util.List;

import model.db.Item;

/**
 * DAO of Resources
 * @author Zhou TQ
 * @since 2016/7/10
 * @version
 *      0 origin one
 *      1 more func support
 *      1.1 FindItemByUsername changed 2016/7/28 by Xu
 */
public interface ItemDao{
	/**
	 * 获取最近的资源——所有人的
	 * @param timestamp
	 * @param amount
	 * @return resource
	 *      返回权限 —— public
	 */
	public List<Item> getItems(Timestamp timestamp, int amount);

	/**
     * 通过resource的id来找到某一个物品
     * @param id
     * @return 这个resource
     *      没有做权限检查，由service层来检查
     */
    public Item FindItemById(int id);

	/**
	 * 通过用户名获取全部资源
     * @param name
     * @return 全部资源
     *         if Timestamp is null -- give for a certain amount name's resource
     *         if amount is no more than 0 -- return all back
	 *         if the auth is false -- only return public resources
     */
    public List<Item> FindItemByUsername(String name, Timestamp timestamp, int amount, boolean auth);

	/**
	 * 获取一堆用户的最新资源
	 * @param names
	 * @param timestamp
	 * @param amount
	 * @return 全部资源，仅仅包含Public的部分
	 *         if Timestamp is null -- give for a certain amount name's resource
	 *         if amount is no more than 0 -- return all back
	 */
	public List<Item> findItemOfUserFriend(List<String> names, Timestamp timestamp, int amount);

	/**
	 * 更新、新增、删除一个resource
     * @param item
     */
    public void updateItem(Item item);
    public void saveObject(Item item);
    public void deleteItem(Item item);
    
}
