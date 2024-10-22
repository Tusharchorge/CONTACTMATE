package com.Contax;

import com.Contax.Entities.User;
import com.Contax.Helper.AppConstants;
import com.Contax.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class ContaXApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ContaXApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Check if admin user exists
		String adminEmail = "admin@gmail.com";
		Optional<User> existingUser = userRepo.findByEmail(adminEmail);

		if (existingUser.isPresent()) {
			System.out.println("Admin user already exists.");
		} else {
			// Create admin user if not present
			User adminUser = new User();
			adminUser.setUserId(UUID.randomUUID().toString());
			adminUser.setName("admin");
			adminUser.setEmail(adminEmail);
			adminUser.setPassword(passwordEncoder.encode("admin"));
			adminUser.setRoleList(Arrays.asList(AppConstants.ROLE_USER)); // You may want to add ROLE_ADMIN here
			adminUser.setEmailVerified(true);
			adminUser.setEnabled(true);
			adminUser.setAbout("This is a dummy admin user created initially.");
			adminUser.setPhoneVerified(true);

			userRepo.save(adminUser);
			System.out.println("Admin user created.");
		}
	}
}
