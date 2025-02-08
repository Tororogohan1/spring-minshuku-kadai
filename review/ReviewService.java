package com.example.samuraitravel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewForm;
import com.example.samuraitravel.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public List<Review> findReviewsByHouse(House house) {
        return reviewRepository.findByHouseId(house);
    } 
    public Optional<Review> findReviewById(int id) {
        return reviewRepository.findById(id);
    }
    

    // **レビューの登録**
    public void createReview(House house, User user, ReviewForm reviewForm) { // **ReviewForm を使用**
        Review newReview = new Review();
        newReview.setHouse(house);
        newReview.setUser(user);
        newReview.setRating(reviewForm.getRating()); // **ReviewForm から取得**
        newReview.setComment(reviewForm.getComment()); // **ReviewForm から取得**
        reviewRepository.save(newReview);
    }
    public void deleteReviewById(int id) {
        reviewRepository.deleteById(id);
    }
}
