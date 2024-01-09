package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Scheduled(initialDelay = 500L, fixedRate = 1000L)
//	public void start1(){
//		System.out.println("1. " + new Date());
//	}
//
//	@Scheduled(fixedDelay = 1000L)
//	public void start2(){
//		System.out.println("2. " + new Date());
//	}
//
//	@Scheduled(cron = "0 23 14 * * *")
//	public void start3(){
//		System.out.println("3. " + new Date());
//	}
}
