package com.example.demo.controllers;

import com.example.demo.beans.Job;
import com.example.demo.beans.User;
import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String listJobs(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        if(userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "index";
    }

    @GetMapping("/add")
    public String jobForm(Model model){
        model.addAttribute("job", new Job());
        return "jobform";
    }

    // process Search Bar form
    @PostMapping("/processsearch")
    public String searchResult(Model model, @RequestParam(name="search") String search){
        model.addAttribute("jobs", jobRepository.findByTitleContainingIgnoreCase(search));
        return "searchlist";
    }

    @PostMapping("/process")
    public String processForm(@ModelAttribute Job job){
        job.setUser(userService.getUser());
        jobRepository.save(job);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUser());
        model.addAttribute("job", jobRepository.findById(id).get());
        if (userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUser());
        model.addAttribute("job", jobRepository.findById(id).get());
        if (userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "jobform";
    }

    @RequestMapping("delete/{id}")
    public String delJob(@PathVariable("id") long id){
        jobRepository.deleteById(id);
        return "redirect:/";
    }

    }
