package controller;
import model.db.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import DAO.imple.UserDaoImpl;
import model.SimpleUser;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        RestTemplate rt = new RestTemplate();
        String a = rt.postForObject("http://localhost:8080/login", new SimpleUser("name1","pwd1"), String.class);
        System.out.println(a);
        
        UserDaoImpl ud = new UserDaoImpl();
        User user = ud.FindUser("name1", "pwd1");
        System.out.println(user.getEmail());
    }
}
