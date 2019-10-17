package com.example.demo.repositories;

import com.example.demo.beans.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface JobRepository extends CrudRepository<Job, Long> {
    ArrayList<Job> findByTitleContainingIgnoreCase(String title);
}
