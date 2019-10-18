package com.example.demo.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime posteddate;

    private String author;

    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Job() {
        posteddate = LocalDateTime.now();
    }

    public Job(String title, String description, LocalDateTime posteddate, String author, String phone) {
        this.title = title;
        this.description = description;
        this.posteddate = posteddate;
        this.author = author;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPosteddate() {
        return posteddate;
    }

    public void setPosteddate(LocalDateTime posteddate) {
        this.posteddate = posteddate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
