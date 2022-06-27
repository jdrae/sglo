package io.sglo.account.user.dto;

import io.sglo.account.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String realname;
    private String nickname;
    private String email;

    //TODO: change to "of"
    @Builder
    public UserInfoResponse(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.realname = user.getRealname();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
