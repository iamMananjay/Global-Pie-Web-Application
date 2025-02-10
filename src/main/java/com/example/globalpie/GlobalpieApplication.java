package com.example.globalpie;

import com.example.globalpie.model.Users;
import com.example.globalpie.repository.UsersRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GlobalpieApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalpieApplication.class, args);
	}
		@Bean
		CommandLineRunner initDatabase(UsersRepo userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Check if the user already exists
			if (!userRepository.findByEmail("manu@gmail.com").isPresent()) {
				Users user = new Users();
				user.setName("Admin User");
				user.setEmail("manu@gmail.com");
				user.setPassword(passwordEncoder.encode("Wildeverm@nu2055"));
				user.setContact("9860252886");
				user.setStatus("active");
				user.setGender("Male");
				user.setUserRole("admin");
				userRepository.save(user);
				System.out.println("Admin user created!");
			}
		};
	}

}
