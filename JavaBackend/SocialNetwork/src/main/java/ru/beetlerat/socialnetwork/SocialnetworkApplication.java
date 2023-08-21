package ru.beetlerat.socialnetwork;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
@ConfigurationPropertiesScan("ru.beetlerat.socialnetwork")
@SpringBootApplication
public class SocialnetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialnetworkApplication.class, args);
	}

	// Синглтон объект ModelMapper, для маппинга DTO
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


}
