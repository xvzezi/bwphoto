package service;
import java.io.File;
import java.io.InputStream;

/**
 * Created by hasee on 2016/7/7.
 */
public interface ProfileService
{
    public boolean saveProfile(String name, InputStream image);
    public InputStream getProfile(String name);
}
