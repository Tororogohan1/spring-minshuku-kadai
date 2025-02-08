package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;



@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 特定の house_id に関連するレビューを取得
    public List<Review> findByHouseId(House house);

    // 特定の user_id に関連するレビューを取得
    public List<Review> findByUserId(User user);

    // 評価スコアが一定以上のレビューを取得
    public List<Review> findByRatingGreaterThanEqual(int rating);

    // 特定の house_id と user_id に関連するレビューを取得
    public List<Review> findByHouseIdAndUserId(House house, User user);
    
    // 
    public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);

}