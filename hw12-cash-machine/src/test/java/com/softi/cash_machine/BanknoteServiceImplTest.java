package com.softi.cash_machine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BanknoteServiceImplTest {

    static Comparator<Map<BanknoteType, Integer>> banknoteBundleComparator = getBanknoteBundleComparator();
    
    @ParameterizedTest
    @MethodSource("getBanknotes")
    void getBanknotes(int sum, Map<BanknoteType, Integer> banknoteCells, Map<BanknoteType, Integer> expectedResult) {
        BanknoteServiceImpl banknoteService = new BanknoteServiceImpl();
        banknoteService.setBanknoteCellMap(banknoteCells);
        banknoteService.setBanknoteSearchService(new BanknoteSearchServiceBfsImpl());
        
        Map<BanknoteType, Integer> result = banknoteService.getBanknotes(sum);

        assertEquals(0, banknoteBundleComparator.compare(expectedResult, result));
    }

    static Stream<Arguments> getBanknotes() {
        Map<BanknoteType, Integer> banknoteMap1 = createEmptyMap();
        for (BanknoteType banknoteType : BanknoteType.values()) {
            banknoteMap1.put(banknoteType, 20);
        }
        
        int amount1 = 750;
        var expectedResult1 = createEmptyMap();
        expectedResult1.put(BanknoteType.RUB_500, 1);
        expectedResult1.put(BanknoteType.RUB_200, 1);
        expectedResult1.put(BanknoteType.RUB_50, 1);

        int amount2 = 1400;
        var expectedResult2 = createEmptyMap();
        expectedResult2.put(BanknoteType.RUB_1000, 1);
        expectedResult2.put(BanknoteType.RUB_200, 2);

        int amount3 = 25500;
        var expectedResult3 = createEmptyMap();
        expectedResult3.put(BanknoteType.RUB_5000, 5);
        expectedResult3.put(BanknoteType.RUB_500, 1);

        int amount4 = 13600;
        var expectedResult4 = createEmptyMap();
        expectedResult4.put(BanknoteType.RUB_5000, 2);
        expectedResult4.put(BanknoteType.RUB_2000, 1);
        expectedResult4.put(BanknoteType.RUB_1000, 1);
        expectedResult4.put(BanknoteType.RUB_500, 1);
        expectedResult4.put(BanknoteType.RUB_100, 1);

        int amount5 = 57850;
        var expectedResult5 = createEmptyMap();
        expectedResult5.put(BanknoteType.RUB_5000, 11);
        expectedResult5.put(BanknoteType.RUB_2000, 1);
        expectedResult5.put(BanknoteType.RUB_500, 1);
        expectedResult5.put(BanknoteType.RUB_200, 1);
        expectedResult5.put(BanknoteType.RUB_100, 1);
        expectedResult5.put(BanknoteType.RUB_50, 1);

        int amount6 = 93450;
        var expectedResult6 = createEmptyMap();
        expectedResult6.put(BanknoteType.RUB_5000, 18);
        expectedResult6.put(BanknoteType.RUB_2000, 1);
        expectedResult6.put(BanknoteType.RUB_1000, 1);
        expectedResult6.put(BanknoteType.RUB_500, 0);
        expectedResult6.put(BanknoteType.RUB_200, 2);
        expectedResult6.put(BanknoteType.RUB_100, 0);
        expectedResult6.put(BanknoteType.RUB_50, 1);

        int amount7 = 600;
        Map<BanknoteType, Integer> banknoteMap2 = createEmptyMap();
        banknoteMap2.put(BanknoteType.RUB_500, 2);
        banknoteMap2.put(BanknoteType.RUB_200, 3);
        var expectedResult7 = createEmptyMap();
        expectedResult7.put(BanknoteType.RUB_200, 3);
        
        return Stream.of(
                Arguments.of(amount1, banknoteMap1, expectedResult1),
                Arguments.of(amount2, banknoteMap1, expectedResult2),
                Arguments.of(amount3, banknoteMap1, expectedResult3),
                Arguments.of(amount4, banknoteMap1, expectedResult4),
                Arguments.of(amount5, banknoteMap1, expectedResult5),
                Arguments.of(amount6, banknoteMap1, expectedResult6),
                Arguments.of(amount7, banknoteMap2, expectedResult7)
        );
    }
    
    @Test
    void getBanknotes_notEnoughMoneyInCashMachine() {
        BanknoteServiceImpl banknoteService = new BanknoteServiceImpl();
        banknoteService.setBanknoteCellMap(new BanknoteBundle().getBanknoteMap());
        banknoteService.setBanknoteSearchService(new BanknoteSearchServiceBfsImpl());

        int sum = 100;

        assertThrows(IllegalStateException.class, () -> banknoteService.getBanknotes(sum));
    }
    
    @Test
    void getBanknotes_noSuchBanknotesToGive() {
        BanknoteServiceImpl banknoteService = new BanknoteServiceImpl();
        BanknoteBundle banknoteBundle = new BanknoteBundle();
        banknoteBundle.addBanknotes(BanknoteType.RUB_500, 1);
        banknoteService.setBanknoteCellMap(banknoteBundle.getBanknoteMap());
        banknoteService.setBanknoteSearchService(new BanknoteSearchServiceBfsImpl());
        

        int sum = 100;

        assertThrows(IllegalStateException.class, () -> banknoteService.getBanknotes(sum));
    }

    private static Map<BanknoteType, Integer> createEmptyMap() {
        Map<BanknoteType, Integer> emptyMap = new HashMap<>();
        for (BanknoteType banknoteType : BanknoteType.values()) {
            emptyMap.put(banknoteType, 0);
        }
        return emptyMap;
    }

    private static Comparator<Map<BanknoteType, Integer>> getBanknoteBundleComparator() {
        return (o1, o2) -> {
            for (BanknoteType banknoteType : BanknoteType.values()) {
                int compareResult = o1.get(banknoteType).compareTo(o2.get(banknoteType));
                if (compareResult != 0) {
                    return compareResult;
                }
            }
            return 0;
        };
    }
}