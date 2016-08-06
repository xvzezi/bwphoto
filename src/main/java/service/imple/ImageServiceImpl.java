package service.imple;

import DAO.BookDao;
import DAO.ImageDao;
import DAO.ItemDao;
import model.db.Book;
import model.db.Image;
import model.db.Item;
import service.ImageService;
import util.Log;

/**
 * Created by hasee on 2016/7/11.
 */
public class ImageServiceImpl implements ImageService
{
	//auto wired
	ItemDao itemDao;
	ImageDao imageDao;
	BookDao bookDao;
	@Override
	public byte[] getImgDetailByItem(int item_id)
	{
		Item item = itemDao.FindItemById(item_id);
		if(item == null || item.getImageId() == null)
		{
			return null;
		}

		Image image = imageDao.FindImageById(item.getImageId());
		return image.getImage();
	}

	@Override
	public boolean createImgDetailByItem(String username, int item_id, byte[] image)
	{
		Item item = itemDao.FindItemById(item_id);
		if(item == null || !item.getUserName().equals(username))
		{
			return false;
		}

		Image img = new Image();
		img.setImage(image);
		try
		{
			imageDao.saveObject(img);
			item.setImageId(img.getId());
			itemDao.updateItem(item);
		}catch(Exception e)
		{
			Log.log.log("Error int create img:").log(e.getMessage()).log();
			return false;
		}

		return true;
	}


	/**
	 * 检查是否有isbn号，如果有返回对应连接
	 * @param item_id
	 */
	public String getBookUrl(int item_id)
	{
		Item item = itemDao.FindItemById(item_id);
		if(item == null || item.getIsbn() == null)
		{
			return null;
		}
		Book book = bookDao.FindBookByISBN(item.getIsbn());
		if(book == null || book.getIntro() == null)
		{
			return null;
		}
		return book.getIntro();
	}

	public ItemDao getItemDao()
	{
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao)
	{
		this.itemDao = itemDao;
	}

	public ImageDao getImageDao()
	{
		return imageDao;
	}

	public void setImageDao(ImageDao imageDao)
	{
		this.imageDao = imageDao;
	}

	public void setBookDao(BookDao bookDao) { this.bookDao = bookDao; }
}
