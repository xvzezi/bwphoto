package controller;

import model.RegMes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import service.ImageService;
import util.Log;
import util.SpringIoC;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 资源内图片获取
 * @since 2016/7/11
 * @author Xu Zezi
 */
@RestController
public class Image
{
	/**
	 * 获取图片
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/image", method = RequestMethod.GET)
	public Object getImg(@PathVariable int resource_id, HttpSession session)
	{
		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return ResponseEntity.badRequest().body(null);
		}

		//normal one
		ImageService imgService = SpringIoC.idGetter("imageService", ImageService.class);
		byte[] image = imgService.getImgDetailByItem(resource_id);
		InputStream is = null;
		if(image == null)
		{
			// try if it is a book resource
			String url = imgService.getBookUrl(resource_id);
			if(url != null)// then redirect to the target resource
			{
				Log.log.log(url).log();
				return new ModelAndView("redirect:"+url);
			}//failed
			//cannot get the pic get the default png
			ClassPathResource cpr = new ClassPathResource("/default/none.jpg");
			try{
				is = cpr.getInputStream();
			}catch (IOException e){
				//file not found
				Log.log.log("default image open error:").log(e.getMessage()).log();
				return ResponseEntity.badRequest().body(null);
			}
			return ResponseEntity.ok()
					.headers(new HttpHeaders())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new InputStreamResource(is));
		}
		is = new ByteArrayInputStream(image);
		return ResponseEntity.ok()
				.headers(new HttpHeaders())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(is));
	}
}
