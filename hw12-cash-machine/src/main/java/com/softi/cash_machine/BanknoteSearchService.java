package com.softi.cash_machine;

import java.util.Map;

public interface BanknoteSearchService {

    Map<BanknoteType, Integer> searchBanknoteBundle(
            Map<BanknoteType, Integer> banknoteCells, int sum);
}
