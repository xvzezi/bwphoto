package controller;

import DAO.ItemDao;
import model.response.IntegerMes;
import model.db.Item;
import model.db.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.RecService;
import service.ResourceService;
import util.SpringIoC;
import util.StatisticUtil;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 推荐服务
 * @author Xu Zezi
 * @since 2016/7/12
 * @version
 *      0
 *      1   Detailed Implementation 2016/8/4
 */
@RestController
public class Recommand
{
	/**
	 * 获取登录用户次数
	 * @auth PUBLIC
	 * @return amount
	 */
	@RequestMapping(value = "/statistics/visitors", method = RequestMethod.GET)
	public IntegerMes getVisitorCount()
	{
		return new IntegerMes(StatisticUtil.visitorCounter);
	}

	/**
	 * 获取新的资源数目
	 * @auth PUBLIC
	 * @return amount
	 */
	@RequestMapping(value = "/statistics/resources/new", method = RequestMethod.GET)
	public IntegerMes getResourcesCount()
	{
		return new IntegerMes(StatisticUtil.newResource);
	}

	/**
	 * 获取成对朋友的数目
	 * @auth PUBLIC
	 * @return amount
	 */
	@RequestMapping(value = "/statistics/friends/new", method = RequestMethod.GET)
	public IntegerMes getFriendsCount()
	{
		return new IntegerMes(StatisticUtil.friendsMade);
	}

	@RequestMapping(value = "/statistics/resources", method = RequestMethod.GET)
	public IntegerMes getResourceCount() { return new IntegerMes(SpringIoC.idGetter("itemDao", ItemDao.class).getAmount()); }

	@RequestMapping(value = "/statistics/friends", method = RequestMethod.GET)
	public IntegerMes getTotalFriends() { return new IntegerMes(0); }

	/**
	 * Zombie Test Recommend
	 * @Deprecated
	 * @return
	 */
	@RequestMapping(value = "/rec/tags", method = RequestMethod.GET)
	public List<User> getUsers()
	{
		User robot = new User();
		robot.setName("Server Robot");
		List<User> us = new ArrayList<>();
		us.add(robot);
		return us;
	}

	/**
	 * Recommend Service
	 * Description:
	 *      It use 4 degree to value the thought of people.
	 *      The emotion, type; friends' emotion and type,
	 *      these 4 way to balance to get what they want.
	 */
	@RequestMapping(value = "/recommend/resource", method = RequestMethod.GET)
	public List<Item> getRecResource(HttpSession session)
	{
		//if not logged, return the latest resources
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			List<Item> items = SpringIoC.idGetter("resourceService", ResourceService.class).getLatestResource(null, 10);
			return items;
		}

		// check the same compatible resources, and return
		return null;
	}

	@RequestMapping(value = "/recommend/user", method = RequestMethod.GET)
	public List<String> getRecUsers(HttpSession session)
	{
		// if not logged, just return null
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return null;
		}

		// check the same compare target users, and return
		return SpringIoC.idGetter("recService", RecService.class).getRecFriends(name);
	}

}
