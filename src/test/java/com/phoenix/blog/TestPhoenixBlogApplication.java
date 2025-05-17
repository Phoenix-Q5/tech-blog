package com.phoenix.blog;

import org.springframework.boot.SpringApplication;

public class TestPhoenixBlogApplication {

	public static void main(String[] args) {
		SpringApplication.from(PhoenixBlogApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
