package com.example.libraryManagementSystem;

import com.example.libraryManagementSystem.entity.User;
import com.example.libraryManagementSystem.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Log4j2
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}
    @Bean
    CommandLineRunner initData(UserService userService, BCryptPasswordEncoder encoder) {
        return args -> {
            try {
                if (userService.findByUsername("admin") == null) {
                    userService.save(new User(null, "admin", encoder.encode("admin"), "ADMIN", true,null));
                    log.info("✓ Admin user created: admin/admin");
                }
                if (userService.findByUsername("user") == null) {
                    userService.save(new User(null, "user", encoder.encode("user"), "USER", true,null));
                    log.info("✓ Regular user created: user/user");
                }
            } catch (Exception e) {
                log.warn("Users may already exist or error during initialization: {}", e.getMessage());
            }
        };
    }

}
