package service;

import model.db.Friend;
import model.db.FriendRequest;

import java.util.List;

/**
 * Friend Request Service
 * @author Xu Zezi
 * @since 2016/7/13.
 */
public interface FriendService
{
	/*********************************************获取好友与好友管理***************************************************/
	/**
	 * 获取目前的所有的好友
	 * @param name
	 * @return 好友们
	 */
	public List<Friend> getFriends(String name);


	/**
	 * 删除一个好友
	 * @param name
	 * @param tar
	 * @return 成功与否
	 */
	public boolean deleteFriend(String name, String tar);

	/*********************************************申请好友与审核部分***************************************************/
	/**
	 * 获取申请人的所有申请
	 * @param applyer
	 * @return 所有申请
	 *      方法会将所有表示为通过或者拒绝的请求，在这一次拿出数据库后删除。保留待审核的请求
	 */
	public List<FriendRequest> getApplyerRequest(String applyer);

	/**
	 * 获取被申请人的所有受理申请
	 * @param applyee
	 * @return 所有申请
	 *      方法只会返回待审核的请求
	 */
	public List<FriendRequest> getApplyeeRequest(String applyee);

	/**
	 * 修改审核状态
	 * @param applyer
	 * @param applyee
	 * @param newStatus
	 * @return 完成度信息
	 *      方法禁止修改已经审核过的请求，只允许修改待审核的请求。
	 */
	public String changeRequestStatus(String applyer, String applyee, int newStatus);

	/**
	 * 提出申请
	 * @param applyer
	 * @param applyee
	 * @return 完成度信息
	 *      无
	 */
	public String applyFor(String applyer, String applyee);
}
