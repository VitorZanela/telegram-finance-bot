package project.telegramfinancebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChatbotgastosApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChatbotgastosApplication.class, args);
	}

}
