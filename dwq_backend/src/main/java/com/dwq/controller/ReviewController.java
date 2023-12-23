package com.dwq.controller;


import com.dwq.entity.dto.Review;
import com.dwq.service.impl.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public void addReview(@RequestBody Review review) {
        reviewService.addReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Integer reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @PutMapping("/{id}")
    public void updateReview(@RequestBody Review review,@PathVariable("id") Integer reviewId) {
        review.setReviewId(reviewId);
        reviewService.updateReview(review);
    }

    @GetMapping("/{id}")
    public List<Review> getReview(@PathVariable("id") Integer reviewId) {
        return reviewService.getReview(reviewId);
    }

    @GetMapping("/")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }
}
