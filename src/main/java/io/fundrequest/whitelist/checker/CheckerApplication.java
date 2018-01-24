package io.fundrequest.whitelist.checker;

import io.fundrequest.whitelist.checker.load.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CheckerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(CheckerApplication.class, args);
		RegistrationService registrationService = run.getBean(RegistrationService.class);
		registrationService.load();
	}
}
