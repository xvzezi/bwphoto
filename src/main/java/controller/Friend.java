package controller;

import model.RegMes;
import model.db.FriendRequest;
import model.db.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.FriendService;
import util.SpringIoC;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.List;

/**
 * Service About making friends
 * @author Xu Zezi
 * @since 2016/7/13
 * @version
 *      0   api formed
 *      1   API implemented     2016/7/14
 */

@RestController
public class Friend
{
	/*************************************************获取好友信息*****************************************************/
	/**
	 * 获取所有的好友
	 * @auth PROTECTED
	 * @param session
	 * @return 好友列表
	 */
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public List<model.db.Friend> getFriends(HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null) // not logged in
		{
			return null;
		}

		// return the results
		return SpringIoC.idGetter("friendService", FriendService.class).getFriends(name);
	}

	/**
	 * 删除一个好友
	 * @param username
	 * @param session
	 * @return 删除信息
	 */
	@RequestMapping(value = "/friends/{username}", method = RequestMethod.DELETE)
	public RegMes deleteFriend(@PathVariable String username, HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null || name.equals(username)) // not logged in
		{
			RegMes rm = new RegMes();
			rm.setFail("Permission Denied");
			return rm;
		}

		// do the request
		boolean result = SpringIoC.idGetter("friendService", FriendService.class).deleteFriend(name, username);

		// return the message
		RegMes rm = new RegMes();
		if(result)
		{
			rm.setSuccess("success");
		}
		else
		{
			rm.setFail("No such friend or other reasons.");
		}
		return rm;
	}

	/*************************************************发送好友请求*****************************************************/
	/**
	 * 交朋友——递交申请，加好友
	 * @auth PROTECTED
	 * @param username
	 * @param session
	 * @return message
	 * 事实上，这里需要实现的应该是加入申请队列。当另一方同意，形成好友配对。
	 */
	@RequestMapping(value = "/friends/apply/{user_name}", method = RequestMethod.POST)
	public RegMes makeFriend(@PathVariable("user_name") String username, HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null || name.equals(username)) // not logged in
		{
			RegMes rm = new RegMes();
			rm.setFail("Permission Denied");
			return rm;
		}

		// the code in the service should check whether the target username exists
		FriendService frs = SpringIoC.idGetter("friendService", FriendService.class);
		String result = frs.applyFor(name, username);

		// return the result
		RegMes rm = new RegMes();
		if(result.equals("success"))
		{
			rm.setSuccess("application success");
		}
		else
		{
			rm.setFail(result);
		}
		return rm;
	}

	/**
	 * 交朋友——查看申请状态
	 * @auth PROTECTED
	 * @param session
	 * @return 包括成功与否在内的用户信息
	 *      查看一次后，失败与成功的信息将会被删除，只保留待处理的信息
	 */
	@RequestMapping(value = "/friends/apply", method = RequestMethod.GET)
	public List<FriendRequest> getApplyTable(HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null) // not logged in
		{
			return null;
		}

		//create service, check has been done inside the service
		FriendService frs = SpringIoC.idGetter("friendService", FriendService.class);
		return frs.getApplyerRequest(name);
	}

	/**************************************************处理好友请求****************************************************/
	/**
	 * 交朋友——获取申请加自己为好友的用户列表
	 * @auth PROTECTED
	 * @param session
	 * @return 需要确认的列表
	 */
	@RequestMapping(value = "/friends/make", method = RequestMethod.GET)
	public List<FriendRequest> getFriendToMake(HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null) // not logged in
		{
			return null;
		}

		//create service, check has been done inside the service
		FriendService frs = SpringIoC.idGetter("friendService", FriendService.class);
		return frs.getApplyeeRequest(name);
	}

	/**
	 * 交朋友——获取某个申请人 的具体信息
	 * @auth PROTECTED TODO
	 * @param username
	 * @param session
	 * @return 具体信息包括 同样的tag，因为什么加我为好友
	 */
	@RequestMapping(value = "/friends/make/{username}", method = RequestMethod.GET)
	public User getFriendDetail(@PathVariable String username, HttpSession session)
	{
		return new User();
	}

	/**
	 * 交朋友——同意这个人为自己的好友
	 * @auth PROTECTED
	 * @param username
	 * @param session
	 * @return 成功或者失败信息
	 */
	@RequestMapping(value = "/friends/make/{username}", method = RequestMethod.POST)
	public RegMes agreeToMakeFriend(@PathVariable String username, HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null || name.equals(username)) // not logged in
		{
			RegMes rm = new RegMes();
			rm.setFail("Permission Denied");
			return rm;
		}

		// do the request, in the same time, the two should have relations of friends
		FriendService frs = SpringIoC.idGetter("friendService", FriendService.class);
		String result = frs.changeRequestStatus(name, username, 'y');
		RegMes rm = new RegMes();
		if(result.equals("success"))
		{
			rm.setSuccess("agree success");
		}
		else
		{
			rm.setFail(result);
		}
		return rm;
	}

	/**
	 * 交朋友——拒绝这个人为自己的好友
	 * @auth PROTECTED
	 * @param username
	 * @param session
	 * @return 成功或者失败信息
	 */
	@RequestMapping(value = "/friends/make/{username}", method = RequestMethod.DELETE)
	public RegMes disagreeToMake(@PathVariable String username, HttpSession session)
	{
		// check the session
		String name = (String)session.getAttribute("name");
		if(name == null || name.equals(username)) // not logged in
		{
			RegMes rm = new RegMes();
			rm.setFail("Permission Denied");
			return rm;
		}

		// do the request, in the same time, no relations built
		FriendService frs = SpringIoC.idGetter("friendService", FriendService.class);
		String result = frs.changeRequestStatus(name, username, 'n');
		RegMes rm = new RegMes();
		if(result.equals("success"))
		{
			rm.setSuccess("reject success");
		}
		else
		{
			rm.setFail(result);
		}
		return rm;
	}

	/**********************************************************END*****************************************************/
}
