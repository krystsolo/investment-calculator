package pl.fintech.dragonsinvestments.investmentcalculator.domain.basket;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
class BasketResultAssembler {

    BasketResult toResult(Basket basket, BasketProfitCalculation finalBasketValue) {
        RiskType riskType = basket.getRiskType();
        return BasketResult.builder()
                .id(basket.getId())
                .basketValue(round(basket.getValue()))
                .riskType(basket.getRiskType())
                .totalAmount(
                        round(finalBasketValue.getFinalBasketValue()))
                .profit(new Profit(
                        round(finalBasketValue.getFinalCash().subtract(basket.cashValue())),
                        round(finalBasketValue.getFinalBonds().subtract(basket.bondsValue())),
                        round(finalBasketValue.getFinalStock().subtract(basket.stocksValue()))))
                .percentage(new PercentageTypeInwestment(
                        toPercentageValue(riskType.cashPart()),
                        toPercentageValue(riskType.bondsPart()),
                        toPercentageValue(riskType.stocksPart())))
                .build();
    }

    private BigDecimal round(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_UP);
    }

    private double toPercentageValue(BigDecimal part) {
        return part.doubleValue() * 100;
    }
}