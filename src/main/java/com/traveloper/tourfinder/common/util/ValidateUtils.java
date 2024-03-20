package com.traveloper.tourfinder.common.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidateUtils {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#$]).{8,20}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * <p>비밀번호 검증 메서드</p>
     * 8글자 이상 20글자 이하, 특수 문자 !@#$ 중 1개 이상 포함, 영어 대문자 포함
     *
     * @return 통과시 true 통과 못하면 false
     * */
    public static boolean isValidPassword(String password) {
        return password != null && passwordPattern.matcher(password).matches();
    }




    /**
     * <p>이메일 검증 메서드</p>
     * 이메일 형식이 올바른지 체크 test@test.test 형태
     * @return 통과시 true 통과 못하면 false
     * */
    public static boolean isValidEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }

}
