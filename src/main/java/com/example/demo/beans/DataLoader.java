package com.example.demo.beans;

import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
  @Autowired
  UserRepository userRepository;

  @Autowired
  JobRepository jobRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... strings) throws Exception {
    System.out.println("Loading data...");

    roleRepository.save(new Role("USER"));
    roleRepository.save(new Role("ADMIN"));

    Role adminRole = roleRepository.findByRole("ADMIN");
    Role userRole = roleRepository.findByRole("USER");

    User user = new
            User("bob@bob.com", "password", "Bob",
            "Bobberson", true, "bob");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList(userRole));
    userRepository.save(user);

    user = new
            User("jim@jim.com", "password", "Jim",
            "Jimmerson", true, "jim");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList(userRole));
    userRepository.save(user);

    user = new
            User("admin@admin.com", "password",
            "Admin", "User", true, "admin");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList(adminRole));
    userRepository.save(user);

    user = new
            User("sam@everyman.com", "password",
            "Sam", "Everyman", true, "sam");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList(userRole, adminRole));
    userRepository.save(user);

    Job job;
    job = new Job("Landscaper", "Cut grass, tree trimming, and light hauling",
            LocalDateTime.of(2019, Month.OCTOBER,3,10,15,30),
            "Jim Jimmerson", "703-555-3562");
    jobRepository.save(job);

    job = new Job("Nanny", "Part-time helper to aid with children going to and from school.  Help with homework and provide snack in afternoon. Good driving record. References required.",
            LocalDateTime.of(2019, Month.SEPTEMBER,23,15,03,58),
            "Rebecca Smith", "443-555-9840");
    jobRepository.save(job);
  }
}