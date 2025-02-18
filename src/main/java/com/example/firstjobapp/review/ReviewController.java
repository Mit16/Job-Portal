package com.example.firstjobapp.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@PathVariable Long companyId, @RequestBody Review review) {
        boolean isSaved = reviewService.addReview(companyId, review);
        return isSaved ? ResponseEntity.ok("Review Added Successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company Not Found");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        Review review = reviewService.getReview(companyId, reviewId);
        return (review != null) ? ResponseEntity.ok(review)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review review) {
        boolean isUpdated = reviewService.updateReview(companyId, reviewId, review);
        return isUpdated ? ResponseEntity.ok("Review Updated Successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company or Review Not Found");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deletedReview(companyId,reviewId);
        if(isReviewDeleted){
            return new ResponseEntity<>("Review deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Review not deleted",HttpStatus.NOT_FOUND);
        }
    }
}
