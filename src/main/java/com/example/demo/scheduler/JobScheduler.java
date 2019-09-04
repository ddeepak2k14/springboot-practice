package com.example.demo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobScheduler {
	
	@Scheduled(cron = "0 * * * * ?")
	public void timeOutSixMonthOldRequest() throws InterruptedException {
	System.out.println("Scheduler Working");
	
	}

}
