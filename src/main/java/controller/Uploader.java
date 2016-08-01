package controller;

import model.RegMes;
import model.db.*;
import model.db.Image;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.ImageService;
import service.ProfileService;
import util.Log;
import util.SpringIoC;

import javax.servlet.http.HttpSession;

/**
 * Service Of Image Uploader
 * @since 2016/7/11
 * @author Xu Zezi
 * @version
 *      0   basic impl.
 */
@Controller
public class Uploader
{
	/**
	 * 上传某一个资源的图片
	 * @auth PROTECTED
	 * @param resource_id
	 * @param file
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/image", method = RequestMethod.POST)
	@ResponseBody
	public RegMes uploadResourceImage(@PathVariable int resource_id, @RequestParam("textFile") MultipartFile file,
	                                   HttpSession session)
	{
		RegMes rm = new RegMes();

		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			rm.setFail("User Not Logged");
			return rm;
		}

		//store it
		model.db.Image image = new Image();
		boolean result = false;
		try
		{
			ImageService imageService = SpringIoC.idGetter("imageService", ImageService.class);
			result = imageService.createImgDetailByItem(name, resource_id, file.getBytes());
		}catch (Exception e)
		{
			Log.log.log("Error in Uploader:").log(e.getMessage()).log();
			rm.setFail("File not received");
			return rm;
		}
		if(result)
		{
			rm.setSuccess("image upload succeeded");
		}
		else
		{
			rm.setFail("Something's wrong");
		}
		return rm;
	}

	/**
	 * 上传profile
	 * @auth PROTECTED
	 * @param file
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/identity/profile", method = RequestMethod.POST)
	@ResponseBody
	public RegMes uploadProfileImg(@RequestParam("file") MultipartFile file, HttpSession session)
	{
		RegMes rm = new RegMes();

		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			rm.setFail("User Not Logged");
			return rm;
		}

		ProfileService ps = SpringIoC.idGetter("profileService", ProfileService.class);
		boolean result = false;
		try
		{
			result = ps.saveProfile(name, file.getInputStream());
		}catch (Exception e)
		{
			Log.log.log("Error When Upload Profile:").log(e.getMessage()).log();
			rm.setFail("something's wrong");
			return rm;
		}
		if(result)
		{
			rm.setSuccess("image upload succeeded");
		}
		else
		{
			rm.setFail("Something's wrong");
		}
		return rm;
	}
}
