package service.imple;

import DAO.FriendDao;
import DAO.RecDataDao;
import DAO.UserDao;
import controller.Friend;
import model.db.Item;
import model.db.RecData;
import model.db.User;
import model.request.ResourceCreation;
import service.RecService;

import java.util.List;

/**
 * Created by hasee on 2016/9/8.
 */
public class RecServiceImpl implements RecService
{
	// auto wired
	private RecDataDao dataDao;
	public void setDataDao(RecDataDao dataDao)
	{
		this.dataDao = dataDao;
	}
	private UserDao userDao;
	public void setUserDao(UserDao userDao) { this.userDao = userDao; }
	private FriendDao friendDao;
	public void setFriendDao(FriendDao friendDao) { this.friendDao = friendDao; }

	/**
	 * 矫正函数
	 */
	public void resetData(String name, RecData user)
	{
		if(name == null)
			return;
		List<String> names = friendDao.findFriendRetNames(name);
		List<RecData> data = dataDao.getRecDataOfNames(names);
		double f_e = 0, f_t = 0;
		for(RecData rd : data)
		{
			f_e += rd.getEmotion();
			f_t += rd.getType();
		}
		user.setF_emotion(f_e / names.size());
		user.setF_type(f_t / names.size());
		dataDao.updateRecData(user);
	}

	/**
	 * Update for New Friends Made
	 * do it after friend added
	 *
	 * @param username
	 */
	@Override
	public void addFriend(String username, String tar)
	{
		// check if its OK
		if(username == null || tar == null)
		{
			return;
		}

		// check if its valid person
		RecData rcUser = dataDao.getRecData(username);
		if(rcUser == null)
		{
			return;
		}
		RecData rcFri = dataDao.getRecData(tar);
		if(rcFri == null)
		{
			return;
		}

		// do calculating
		int amount = friendDao.getFriendAmount(username);
		double f_emo = (amount * rcUser.getF_emotion() + rcFri.getEmotion()) / (amount + 1);
		double f_type = (amount * rcUser.getF_type() + rcFri.getType()) / (amount + 1);
		rcUser.setF_emotion(f_emo);
		rcUser.setF_type(f_type);
		dataDao.updateRecData(rcUser);
	}

	/**
	 * Update for Deleted Friends
	 * do it after friend deleted
	 *
	 * @param username
	 */
	@Override
	public void deleteFriend(String username, String tar)
	{
		// check if its OK
		if(username == null || tar == null)
		{
			return;
		}

		// check if its valid person
		RecData rcUser = dataDao.getRecData(username);
		if(rcUser == null)
		{
			return;
		}
		RecData rcFri = dataDao.getRecData(tar);
		if(rcFri == null)
		{
			return;
		}

		// do calculating
		int amount = friendDao.getFriendAmount(username);
		double f_emo = (amount * rcUser.getF_emotion() - rcFri.getEmotion()) / (amount - 1);
		double f_type = (amount * rcUser.getF_type() - rcFri.getType()) / (amount - 1);
		if(f_emo < 0 || f_type < 0)
		{
			// reset data
			resetData(username, rcUser);
			return;
		}
		rcUser.setF_emotion(f_emo);
		rcUser.setF_type(f_type);
		dataDao.updateRecData(rcUser);
	}

	/**
	 * Update for New Added Resource
	 * do it after resources added
	 *
	 * @param resourceCreation
	 */
	@Override
	public void addResource(String name, ResourceCreation resourceCreation)
	{
		// check
		if(name == null)
		{
			return;
		}
		User user = userDao.FindUserByName(name);
		if(user == null)
		{
			return;
		}

		// get
		RecData data = dataDao.getRecData(name);
		data.setEmotion((data.getEmotion() * (user.getAmount() - 1) + resourceCreation.getEmotion()) / user.getAmount());
		data.setType((data.getType() * (user.getAmount() - 1) + resourceCreation.getType()) / user.getAmount());

		// store
		dataDao.updateRecData(data);
	}

	/**
	 * Update for Deleted Friends
	 * do it after resources deleted
	 *
	 * @param item
	 */
	@Override
	public void deleteResource(Item item)
	{
		//
		User user = userDao.FindUserByName(item.getUserName());
		if(user == null)
		{
			return;
		}
		int type = 1;
		if(item.getIsbn() != null)
		{
			type = 2;
		}
		else if(item.getMusicId() != null)
		{
			type = 0;
		}

		// get
		RecData data = dataDao.getRecData(item.getUserName());
		data.setEmotion((data.getEmotion() * (user.getAmount() + 1) - item.getEmotion()) / user.getAmount());
		data.setType((data.getType() * (user.getAmount() + 1) - type) / user.getAmount());

		// store
		dataDao.updateRecData(data);

	}

	/**
	 * Get Recommended Friends
	 *
	 * @param username
	 */
	@Override
	public List<String> getRecFriends(String username)
	{
		return dataDao.getRelateNames(username);
	}
}
