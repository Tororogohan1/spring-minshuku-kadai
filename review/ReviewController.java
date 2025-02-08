package com.example.samuraitravel.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewForm;
import com.example.samuraitravel.service.HouseService;
import com.example.samuraitravel.service.ReviewService;
import com.example.samuraitravel.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final HouseService houseService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, HouseService houseService, UserService userService) {
        this.reviewService = reviewService;
        this.houseService = houseService;
        this.userService = userService;
    }

    // **1. 特定の宿泊施設のレビュー一覧表示**
    @GetMapping("/house/{houseId}")
    public String getReviewsByHouse(@PathVariable("houseId") int houseId, Model model) {
        House house = houseService.findHouseById(houseId).orElse(null);
        if (house == null) {
            return "error/404"; // 宿泊施設が見つからない場合
        }

        List<Review> reviews = reviewService.findReviewsByHouse(house);
        model.addAttribute("house", house);
        model.addAttribute("reviews", reviews);

        return "review/list"; // `review/list.html` に遷移
    }

    // **2. レビュー投稿ページ表示**
    @GetMapping("/new/{houseId}")
    public String showReviewForm(@PathVariable("houseId") int houseId, Model model) {
        House house = houseService.findHouseById(houseId).orElse(null);
        if (house == null) {
            return "error/404";
        }

        model.addAttribute("house", house);
        model.addAttribute("reviewForm", new ReviewForm());

        return "review/new"; // `review/new.html` に遷移
    }

    // **3. レビュー投稿処理**
    @PostMapping("/create/{houseId}")
    public String createReview(
            @PathVariable("houseId") int houseId,
            @Valid @ModelAttribute ReviewForm reviewForm,
            BindingResult result,
            Principal principal) {

        if (result.hasErrors()) {
            return "review/new"; // **バリデーションエラー時にフォームを再表示**
        }

        House house = houseService.findHouseById(houseId).orElse(null);
        if (house == null) {
            return "error/404";
    }
    // **3. ログインユーザーの取得**
    User user = userService.findUserByUsername(principal.getName()).orElse(null);
    if (user == null) {
        return "redirect:/login"; // **未ログインならログインページへ**
    }

    // **4. レビューを登録**
    reviewService.createReview(house, user, reviewForm);

    // **5. レビュー投稿後、宿泊施設の詳細ページへリダイレクト**
    return "redirect:/houses/" + houseId; 
}

    // **4. レビュー削除**
    @PostMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") int reviewId, Principal principal) {
    	Optional<Review> reviewOpt = reviewService.findReviewById(reviewId);

        if (reviewOpt == null) {
            return ResponseEntity.notFound().build();
        }
        
        Review review = reviewOpt.get();

        // **ログインユーザーとレビュー投稿者が一致する場合のみ削除**
        if (!review.getUser().getUsername().equals(principal.getName())) {
            return ResponseEntity.status(403).body("権限がありません");
        }

        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.ok("削除しました");
    }
}
