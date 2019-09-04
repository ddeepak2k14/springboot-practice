package com.example.demo.bean;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobScheduler1 {
	
	@Scheduled(cron = "0 * * * * ?")
	public void timeOutSixMonthOldRequest() throws InterruptedException {
	System.out.println("Scheduler 2 Working");
	
	}

}
