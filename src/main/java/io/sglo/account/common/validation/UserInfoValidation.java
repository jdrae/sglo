package io.sglo.account.common.validation;

import io.sglo.account.common.exception.BaseException;
import io.sglo.account.common.exception.UserExceptionType;

public class UserInfoValidation {
    //TODO: add more custom validations for field

    public static String checkNickname(String nickname){
        if(nickname != null) {
            nickname = nickname.replaceAll("\\s+", "");
            if (nickname.length() == 0)
                // TODO: change to validation error
                throw new BaseException(UserExceptionType.INVALID_NICKNAME);
        }
        return nickname;
    }

}
