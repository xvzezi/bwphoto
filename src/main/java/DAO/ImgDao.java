package DAO;

import java.io.InputStream;

/**
 * Created by hasee on 2016/7/7.
 */
public interface ImgDao {
    public String saveImg(String name, InputStream is);
    public InputStream getImg(String name);
}
