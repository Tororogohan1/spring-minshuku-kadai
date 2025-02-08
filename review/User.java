

package com.example.samuraitravel.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // **主キー（ID）**
    
    @Column(name = "name", nullable = false)
    private String name; // **氏名**
    
    @Column(name = "furigana")
    private String furigana; // **ふりがな**
    
    @Column(name = "postal_code")
    private String postalCode; // **郵便番号**
    
    @Column(name = "address")
    private String address; // **住所**
    
    @Column(name = "phone_number")
    private String phoneNumber; // **電話番号**
    
    @Column(name = "email", unique = true, nullable = false)
    private String email; // **メールアドレス（ログイン用）**
    
    @Column(name = "password", nullable = false)
    private String password; // **パスワード**
    
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // **ユーザーの役割**
    
    @Column(name = "enabled", nullable = false)
    private Boolean enabled; // **アカウント有効化フラグ**
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt; // **作成日時**
    
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt; // **更新日時**
    
    @Column(name = "username", unique = true, nullable = false)
    private String username; // **ログイン用ユーザー名**
}
