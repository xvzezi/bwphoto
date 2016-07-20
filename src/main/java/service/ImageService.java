package service;

import model.db.Image;

/**
 * Created by hasee on 2016/7/11.
 */
public interface ImageService
{
	public byte[] getImgDetailByItem(int item_id);
	public boolean createImgDetailByItem(String username, int item_id, byte[] image);
}
