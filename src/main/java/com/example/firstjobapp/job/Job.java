package com.example.firstjobapp.job;

import com.example.firstjobapp.company.Company;
import jakarta.persistence.*;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer maxSalary;
    private Integer minSalary;
    private String location;

    @ManyToOne
    private Company company;

    public Job() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job(Long id, String title, String description, Integer minSalary, Integer maxSalary, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }  // Updated Getter

    public Integer getMinSalary() {
        return minSalary;
    }  // Updated Getter

    public String getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }  // Updated Setter

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }  // Updated Setter

    public void setLocation(String location) {
        this.location = location;
    }
}
