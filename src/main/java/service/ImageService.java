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
	/**
	 * 通过item来获取对应的图片
	 * @param item_id
	 * @return
	 */
	public byte[] getImgDetailByItem(int item_id);

	/**
	 * 创建或者顶替对应图片
	 * @param username
	 * @param item_id
	 * @param image
	 * @return
	 */
	public boolean createImgDetailByItem(String username, int item_id, byte[] image);

	/**
	 * 检查是否有isbn号，如果有返回对应连接
	 * @param item_id
	 */
	public String getBookUrl(int item_id);
}
