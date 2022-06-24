package io.sglo.account.common.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

}
