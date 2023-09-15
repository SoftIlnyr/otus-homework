package com.softi.cash_machine;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class BanknoteSearchServiceBfsImpl implements BanknoteSearchService {

    @Override
    public Map<BanknoteType, Integer> searchBanknoteBundle(
            Map<BanknoteType, Integer> banknoteCells, int sum) {

        BanknoteBundle emptyBundle = new BanknoteBundle();

        Queue<Edge> searchVariants = new ArrayDeque<>();

        for (BanknoteType banknoteType : BanknoteType.values()) {
            if (banknoteCells.get(banknoteType) == 0) {
                continue;
            }
            searchVariants.add(new Edge(emptyBundle, banknoteType));
        }

        BanknoteBundle result = null;
        while (!searchVariants.isEmpty()) {
            Edge edge = searchVariants.poll();
            BanknoteBundle currentVariant = edge.getBanknoteBundle();

            int currentSum = currentVariant.getSum() + edge.getBanknoteType().getValue();
            if (currentSum == sum) {
                currentVariant.addBanknotes(edge.getBanknoteType(), 1);
                result = currentVariant;
                break;
            }
            if (currentSum > sum) {
                continue;
            }
            BanknoteBundle searchVariant = new BanknoteBundle(currentVariant);
            searchVariant.addBanknotes(edge.getBanknoteType(), 1);
            for (BanknoteType banknoteType : Arrays.stream(BanknoteType.values())
                    .filter(banknoteType -> banknoteType.getValue() <= edge.getBanknoteType().getValue())
                    .collect(Collectors.toList())) {
                if (banknoteCells.get(banknoteType) < currentVariant.getBanknoteMap().get(banknoteType) + 1) {
                    continue;
                }
                searchVariants.add(new Edge(searchVariant, banknoteType));
            }
        }

        if (result == null) {
            throw new IllegalStateException("Невозможно выдать указанную сумму");
        }

        return result.getBanknoteMap();
    }

    @Getter
    @AllArgsConstructor
    class Edge {
        private BanknoteBundle banknoteBundle;
        private BanknoteType banknoteType;
    }

}
