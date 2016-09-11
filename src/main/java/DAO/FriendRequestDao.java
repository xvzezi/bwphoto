package DAO;

import controller.Friend;

import java.util.List;

/**
 * 请求接口
 * @author Xu Zezi
 * @since 2016/7/13.
 */
public interface FriendRequestDao
{
	/**
	 * 获取已提交申请
	 * @param applyer
	 * @return FriendRequestDao
	 *      获取来自申请者的所有申请，包括通过与不通过的，未审核的。
	 */
	public List<model.db.FriendRequest> FindRequestByApplyer(String applyer);

	/**
	 * 获取被提交申请
	 * @param applyee
	 * @return FriendRequestDao
	 *      获取申请加自己为好友的列表，提供所有的，需要service根据需要过滤。
	 */
	public List<model.db.FriendRequest> FindRequestByApplyee(String applyee);

	/**
	 * 创建一个新的请求
	 * @param applyer
	 * @param applyee
	 * @return Success or not
	 *      给定申请者与被申请者，默认在待审核状态
	 */
	public boolean createNewRequest(String applyer, String applyee);

	/**
	 * 改变状态
	 * @param applyer
	 * @param applyee
	 * @param newStatus
	 * @return old status
	 *      给定唯一标示，改变为new status并返回old status
	 */
	public int updateStatus(String applyer, String applyee, int newStatus);

	/**
	 * 删除请求
	 * @param tars
	 *      删除一系列请求
	 */
	public void deleteRequest(List<model.db.FriendRequest> tars);
}
