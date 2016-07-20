package service;

import java.sql.Timestamp;
import java.util.List;

import model.db.Mark;

public interface MarkService
{
    public List<Mark> getMarks(Integer item_id, Integer mark_id, String username, Timestamp timestamp, int  pick_size);
    public int createMark(String content, Integer item_id, Integer target, String username);
    public String deleteMark(Integer id, String username);
}
