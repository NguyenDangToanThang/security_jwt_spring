package com.socialnetwork.socialbackend;

import com.socialnetwork.socialbackend.entity.Role;
import com.socialnetwork.socialbackend.entity.User;
import com.socialnetwork.socialbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class SocialBackendApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public SocialBackendApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialBackendApplication.class, args);
    }

    public void run(String... args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(null == adminAccount) {
            User user = User.builder()
                    .id("1")
                    .username("admin@gmail.com")
                    .firstName("Nguyễn")
                    .lastName("Thắng")
                    .role(Role.ADMIN)
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .createdAt(new Date())
                    .build();
            userRepository.save(user);
        }
    }
}
