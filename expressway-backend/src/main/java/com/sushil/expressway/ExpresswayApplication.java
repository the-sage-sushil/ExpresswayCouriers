package com.sushil.expressway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sushil.expressway.entitys.Role;
import com.sushil.expressway.repositories.RoleRepository;

import jakarta.persistence.EntityListeners;

@SpringBootApplication
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public class ExpresswayApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(ExpresswayApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository){
		return args -> {
			if(roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}
		};
	}

}
