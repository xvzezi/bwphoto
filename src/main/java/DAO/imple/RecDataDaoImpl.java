package DAO.imple;

import DAO.RecDataDao;
import model.db.RecData;

import java.util.List;

/**
 * DAO of RecDate
 * @author Xu Zezi
 * @since 2016/8/6
 * @version
 *      0   Basic Impl.
 */
public class RecDataDaoImpl implements RecDataDao
{

	/**
	 * 获取recdata
	 *
	 * @param username
	 * @return 具体数据
	 * 通过用户名获取用户数据
	 */
	@Override
	public RecData getRecData(String username)
	{
		return null;
	}

	/**
	 * 获取一系列数据
	 *
	 * @param names
	 * @return 具体数据
	 * 会在用好友搞得时候用到
	 */
	@Override
	public List<RecData> getRecDataOfNames(List<String> names)
	{
		return null;
	}

	/**
	 * 获取相关的名字
	 *
	 * @param username
	 * @return 一系列名字
	 * 通过某个用户的名字获取数据值匹配的用户
	 */
	@Override
	public List<String> getRelateNames(String username)
	{
		return null;
	}

	/**
	 * 传统的增删改查
	 *
	 * @param username 根据username为唯一识别标识
	 */
	@Override
	public void createRecData(String username)
	{

	}

	@Override
	public void deleteRecData(String username)
	{

	}

	@Override
	public void updateRecData(RecData recData)
	{

	}
}
