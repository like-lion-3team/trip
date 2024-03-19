package com.traveloper.tourfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA 이벤트 리스터 활성화
@EnableJpaAuditing
@SpringBootApplication
public class TourFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourFinderApplication.class, args);
	}

}
