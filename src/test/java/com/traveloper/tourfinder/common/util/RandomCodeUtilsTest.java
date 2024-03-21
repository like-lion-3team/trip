package com.traveloper.tourfinder.common.util;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class RandomCodeUtilsTest {


    @Test
    public void testRandomCodeUtilsDifferentEachTime() {

            final int numberOfTests = 1000;
            Set<String> codes = new HashSet<>();
            for (int i = 0; i < numberOfTests; i++) {
                codes.add(RandomCodeUtils.generate(6));
            }

            double allowedDuplicationPercentage = 1; // 1% 중복 허용
            int expectedUniqueCodes = (int) (numberOfTests * (1 - allowedDuplicationPercentage / 100));
            int actualUniqueCodes = codes.size();
            double actualDuplicationRate = (1 - ((double) actualUniqueCodes / numberOfTests)) * 100;

            System.out.printf("중복 비율: %.2f%%\n", actualDuplicationRate);
            System.out.println("기대 유니크 코드: " + expectedUniqueCodes);
            System.out.println("실제 유니크 코드: " + actualUniqueCodes);

            assertTrue(actualUniqueCodes >= expectedUniqueCodes,
                    "너무 많은 중복 코드 생성. 최소 " + expectedUniqueCodes + "개의 유니크 코드가 예상되었으나, 실제로는 " + actualUniqueCodes + "개만 생성됨.");
        }
}