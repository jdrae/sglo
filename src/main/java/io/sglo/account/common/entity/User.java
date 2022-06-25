package io.sglo.account.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 아이디
    @Column(nullable = false, length = 30, unique = true)
    private String username;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 실명
    @Column
    private String realname;

    // 별명
    @Column(length = 30)
    private String nickname;

    // 이메일
    @Column
    private String email;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
