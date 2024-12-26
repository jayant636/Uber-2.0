package com.project.uber.uberApp;

import com.project.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail("jayantjalandra@gmail.com","testing","Body of my email");
	}

	@Test
	void sendMultipleEmail(){
		String emails[] = {"jayantjalandra@gmail.com","cool.jayant07@gmail.com"};
		emailSenderService.sendEmail(emails,"testing","Body of my email");

	}

}
