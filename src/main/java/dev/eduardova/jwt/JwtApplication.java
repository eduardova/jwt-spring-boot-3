package dev.eduardova.jwt;

import dev.eduardova.jwt.entities.Role;
import dev.eduardova.jwt.entities.User;
import dev.eduardova.jwt.repositories.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import dev.eduardova.jwt.repositories.UserRepository;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passEncoder;

    public static void main(String... args) {
        SpringApplication.run(JwtApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findById(1L).isEmpty()) {
            var adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole = roleRepo.save(adminRole);
            var admin = new User();
            admin.setEmail("admin@admin.com");
            admin.setPassword(passEncoder.encode("123456"));
            admin.setEnabled(Boolean.TRUE);
            admin.setRoles(List.of(adminRole));
            userRepo.save(admin);
        }
    }
}
