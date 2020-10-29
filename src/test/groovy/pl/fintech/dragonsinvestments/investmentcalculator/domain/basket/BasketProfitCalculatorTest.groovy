package pl.fintech.dragonsinvestments.investmentcalculator.domain.basket

import spock.lang.Specification

class BasketProfitCalculatorTest extends Specification {

    BasketProfitCalculator basketProfitCalculator = new BasketProfitCalculator()

    def "should return proper final cash profit value in calculation"() {
        given:
        def basket = new Basket(1000 as BigDecimal, Currency.EUR, RiskType.VERY_CONSERVATIVE)

        when:
        def result = basketProfitCalculator.calculate(basket)

        then:
        result.finalCash == 543.41
    }

    def "should return proper final bonds profit value in calculation"() {
        given:
        def basket = new Basket(1000 as BigDecimal, Currency.EUR, RiskType.VERY_CONSERVATIVE)

        when:
        def result = basketProfitCalculator.calculate(basket)

        then:
        result.finalBonds == 2160.97
    }

    def "should return proper final stock profit value in calculation"() {
        given:
        def basket = new Basket(1000 as BigDecimal, Currency.EUR, RiskType.VERY_CONSERVATIVE)

        when:
        def result = basketProfitCalculator.calculate(basket)

        then:
        result.finalStock == 1522.45
    }

    def "should return proper final basket value in calculation"() {
        given:
        def basket = new Basket(1000 as BigDecimal, Currency.EUR, RiskType.VERY_CONSERVATIVE)

        when:
        def result = basketProfitCalculator.calculate(basket)

        then:
        result.finalBasketValue == 543.41 + 2160.97 + 1522.45
    }
}
