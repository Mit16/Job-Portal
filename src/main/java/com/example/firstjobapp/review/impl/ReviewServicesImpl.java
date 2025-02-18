package com.example.firstjobapp.review.impl;

import com.example.firstjobapp.company.Company;
import com.example.firstjobapp.company.CompanyService;
import com.example.firstjobapp.review.Review;
import com.example.firstjobapp.review.ReviewRepository;
import com.example.firstjobapp.review.ReviewService;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;
import java.util.Optional;

@Service
public class ReviewServicesImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServicesImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        if (companyService.getCompanyById(companyId) == null) {
            throw new EntityNotFoundException("Company with ID " + companyId + " not found.");
        }
        return reviewRepository.findByCompanyId(companyId);
    }


    @Override
    public boolean addReview(Long companyId, Review review) {
        Optional<Company> companyOpt = Optional.ofNullable(companyService.getCompanyById(companyId));
        if (companyOpt.isPresent()) {
            review.setCompany(companyOpt.get());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        return reviewRepository.findById(reviewId)
                .filter(review -> review.getCompany().getId().equals(companyId))
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        Optional<Company> companyOpt = Optional.ofNullable(companyService.getCompanyById(companyId));
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);

        if (companyOpt.isPresent() && reviewOpt.isPresent()) {
            Review existingReview = reviewOpt.get();
            existingReview.setTitle(updatedReview.getTitle());
            existingReview.setDescription(updatedReview.getDescription());
            existingReview.setRating(updatedReview.getRating());
            reviewRepository.save(existingReview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletedReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!= null && reviewRepository.existsById(reviewId)){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            assert review != null;
            Company company = review.getCompany();
            company.getReviews().remove(review);
//            review.setCompany(null);
            companyService.updateCompany(company,companyId);
            reviewRepository.deleteById(reviewId);
            return true;
        }else {
            return false;
        }
    }


}
