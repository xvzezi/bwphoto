package util;

import java.util.Date;

/**
 * Created by hasee on 2016/7/4.
 */
public class MyLog
{
    private String mes = new String();
    public MyLog  log(String message)
    {
        mes += message + ' ';
        return this;
    }
    public void log()
    {
        System.out.print(new Date());
        System.out.println("\t" + mes);
        mes = "";
    }
}