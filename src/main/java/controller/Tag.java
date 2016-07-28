package controller;

import model.RegMes;
import model.db.Itemtag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TagService;
import util.SpringIoC;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2016/7/28.
 */
@RestController
public class Tag
{
	/************************************************资源的Tag*********************************************************/
	/**
	 * 获取resource的tag
	 * @param resource_id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resourec_id}/tags", method = RequestMethod.GET)
	public List<model.db.Tag> getTagsById(@PathVariable int resource_id, HttpSession session)
	{
		//check session
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			return null;
		}

		//
		TagService tagService = SpringIoC.idGetter("tagService", TagService.class);
		List<Itemtag> itemtags = tagService.getTags(name, resource_id);
		if(itemtags == null)
		{
			return null;
		}

		//
		List<model.db.Tag> tags = new ArrayList<model.db.Tag>();
		model.db.Tag tmp = new model.db.Tag();
		for(Itemtag tag : itemtags)
		{
			tmp.setName(tag.getTagName());
			tags.add(tmp);
		}
		return tags;
	}

	/**
	 * 创建tag
	 * @param resource_id
	 * @param tag_name
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resources/{resource_id}/tags/{tag_name}", method = RequestMethod.POST)
	public RegMes createTagOn(@PathVariable int resource_id, @PathVariable String tag_name, HttpSession session)
	{
		//check session
		RegMes rm = new RegMes();
		String name = (String)session.getAttribute("name");
		if(name == null)
		{
			rm.setFail("Not Logged");
			return rm;
		}

		//
		TagService tagService = SpringIoC.idGetter("tagService", TagService.class);
		Itemtag itemtag = new Itemtag();
		itemtag.setTagName(tag_name);
		itemtag.setItemId(resource_id);
		boolean result = tagService.saveObject(name, itemtag);

		if(result)
		{
			rm.setSuccess("succeeded");
		}
		else
		{
			rm.setFail("failed");
		}
		return rm;
	}

}
