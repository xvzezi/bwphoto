package DAO;

import org.hibernate.Session;

import model.db.Image;
import model.db.Item;
import util.HibernateUtil;

public interface ImageDao{
    public Image FindImageById(int id);
	public void updateImage(Image image);
    public void saveObject(Image image);
    public void deleteImage(Image image);
    
}
