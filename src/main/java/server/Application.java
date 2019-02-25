package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Application {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello client";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
