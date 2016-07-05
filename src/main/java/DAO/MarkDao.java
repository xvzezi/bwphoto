package DAO;

import model.db.Mark;

public interface MarkDao 
{
	  public void updateMark(Mark mark);
	  public void saveObject(Mark mark);
	  public void deleteMark(Mark mark);
	  public Mark FindMarkById(int id);
}
