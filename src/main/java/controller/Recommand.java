package controller;

import model.IntegerMes;
import model.RegMes;
import model.db.User;
import org.jboss.logging.annotations.Param;
import org.omg.CORBA.Request;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
	public IntegerMes getResourceCount() { return new IntegerMes(10001); }

	@RequestMapping(value = "/statistics/friends", method = RequestMethod.GET)
	public IntegerMes getTotalFriends() { return new IntegerMes(1009); }
	//TODO: latest resources
	//TODO: connected resources order by time
	//time, tag, age,
	//image character match, 特征值储存在数据库

	/**
	 * Zombie Test Recommend
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

}
