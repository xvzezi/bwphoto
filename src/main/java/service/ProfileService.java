package service;
import java.io.File;
import java.io.InputStream;

/**
 * Service of Profile
 * @author Xu Zezi
 * @since 2016/7/7.
 * @version
 *      0   profile独立业务
 */
public interface ProfileService
{
    public boolean saveProfile(String name, InputStream image);
    public InputStream getProfile(String name);
}
