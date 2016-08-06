package service;

import model.db.Image;

/**
 * Service of Image
 * @sincec 2016/7/11.
 * @author Xu Zezi
 * @version
 *      0   Basic Impl.
 */
public interface ImageService
{
	public byte[] getImgDetailByItem(int item_id);
	public boolean createImgDetailByItem(String username, int item_id, byte[] image);
}
