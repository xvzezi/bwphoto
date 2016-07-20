package service.imple;

import service.ProfileService;
import util.MongoUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 用户头像服务
 * @author Xu Zezi
 * @since 2016/7/7
 * @version
 *      0   basic function
 */
public class ProfileServiceImpl implements ProfileService
{
    @Override
    public boolean saveProfile(String name, InputStream image) {
        try{
            MongoUtil.saveImg("BWPhoto", "profile", name, image);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    @Override
    public InputStream getProfile(String name) {
        return MongoUtil.getImg("BWPhoto", "profile", name);
    }
}
