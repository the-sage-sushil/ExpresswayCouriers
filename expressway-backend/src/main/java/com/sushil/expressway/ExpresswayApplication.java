package com.sushil.expressway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.EntityListeners;

@SpringBootApplication
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public class ExpresswayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpresswayApplication.class, args);
	}

}
