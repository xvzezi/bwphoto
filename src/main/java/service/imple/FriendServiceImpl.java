package service.imple;

import DAO.FriendDao;
import DAO.FriendRequestDao;
import model.db.Friend;
import model.db.FriendRequest;
import service.FriendService;
import util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Friend Request
 * @author Xu Zezi
 * @since 2016/7/14.
 */
public class FriendServiceImpl implements FriendService
{
	/*********************************************获取好友与好友管理***************************************************/
	/**
	 * 获取目前的所有的好友
	 * @param name
	 * @return 好友们
	 */
	public List<Friend> getFriends(String name)
	{
		return fDao.FindFriend(name);
	}

	/**
	 * 删除一个好友
	 * @param name
	 * @param tar
	 * @return 成功与否
	 */
	public boolean deleteFriend(String name, String tar)
	{
		try
		{
			fDao.deleteFriend(name, tar);
		}
		catch (Exception e)
		{
			Log.log.log("Error in Friend Service:").log(e.getMessage()).log();
			return false;
		}
		return true;
	}

	/*********************************************申请好友与审核部分***************************************************/
	/**
	 * 获取申请人的所有申请
	 *
	 * @param applyer
	 * @return 所有申请
	 * 方法会将所有表示为通过或者拒绝的请求，在这一次拿出数据库后删除。保留待审核的请求
	 */
	@Override
	public List<FriendRequest> getApplyerRequest(String applyer)
	{
		// find them out
		List<FriendRequest> frs = frDao.FindRequestByApplyer(applyer);
		if(frs == null) // something's wrong
		{
			return null;
		}

		// select which have been processed
		List<FriendRequest> frd = new ArrayList<FriendRequest>();
		for(FriendRequest fr : frs)
		{
			if(fr.getStatus() != 0)
			{
				frd.add(fr);
			}
		}

		// delete those have been processed
		frDao.deleteRequest(frd);

		// give out the results
		return frs;
	}

	/**
	 * 获取被申请人的所有受理申请
	 *
	 * @param applyee
	 * @return 所有申请
	 * 方法只会返回待审核的请求
	 */
	@Override
	public List<FriendRequest> getApplyeeRequest(String applyee)
	{
		// find them out
		List<FriendRequest> frs = frDao.FindRequestByApplyee(applyee);
		if(frs == null)
		{
			return null;
		}

		// select which need to be processed
		List<FriendRequest> frw = new ArrayList<FriendRequest>();
		for(FriendRequest fr : frs)
		{
			if(fr.getStatus() == 0)
			{
				frw.add(fr);
			}
		}

		// give out the result
		return frw;
	}

	/**
	 * 修改审核状态
	 *
	 * @param applyer
	 * @param applyee
	 * @param newStatus
	 * @return 完成度信息
	 * 方法禁止修改已经审核过的请求，只允许修改待审核的请求。
	 */
	@Override
	public String changeRequestStatus(String applyer, String applyee, int newStatus)
	{
		// provided that only character '-', 'y', 'n', will be noticed

		// try to undone it, reject
		if(newStatus == 0)
		{
			return "Cannot be reset";
		}

		// try to update
		int oldStatus = frDao.updateStatus(applyer, applyee, newStatus);
		Log.log.log(""+oldStatus).log();
		if(oldStatus == 0) // valid
		{
			if(newStatus == 1)
			{
				fDao.addFriend(applyer, applyee);
				fDao.addFriend(applyee, applyer);
			}
			return "success";
		}

		// invalid, rollback
		frDao.updateStatus(applyer, applyee, oldStatus);
		return "Request has been processed";
	}

	/**
	 * 提出申请
	 *
	 * @param applyer
	 * @param applyee
	 * @return 完成度信息
	 * 数据库的外键约束帮助保证applyer 与 applyee真实存在
	 */
	@Override
	public String applyFor(String applyer, String applyee)
	{
		// try to create
		boolean result = frDao.createNewRequest(applyer, applyee);
		if(result)
		{
			return "success";
		}

		// failed
		return "fail";
	}

	/**
	 * Auto-Wired
	 */
	private FriendRequestDao frDao;
	private FriendDao fDao;
	public void setfDao(FriendDao dao) { this.fDao = dao; }
	public void setFrDao(FriendRequestDao dao) { this.frDao = dao; }
}
