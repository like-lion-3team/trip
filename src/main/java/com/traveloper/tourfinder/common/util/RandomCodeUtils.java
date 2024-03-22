package com.traveloper.tourfinder.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * <p>이메일 발송용 랜덤 코드 생성기</p>
 * */
@Component
public class RandomCodeUtils {
    private static final Random RANDOM = new Random();
    public static final String generate(int length){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10)); // Generates a random digit (0-9)
        }
        return code.toString();
    }
}
