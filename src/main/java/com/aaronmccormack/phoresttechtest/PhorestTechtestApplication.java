package com.aaronmccormack.phoresttechtest;

import com.aaronmccormack.phoresttechtest.controller.ClientController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhorestTechtestApplication  {

	public static void main(String[] args) {
		SpringApplication.run(PhorestTechtestApplication.class, args);

	}
}
