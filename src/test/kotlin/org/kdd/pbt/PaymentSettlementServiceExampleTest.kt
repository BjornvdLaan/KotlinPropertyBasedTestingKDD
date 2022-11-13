package org.kdd.pbt

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

class PaymentSettlementServiceExampleTest : FeatureSpec({
    feature("Sum of all balances should always be zero") {
        scenario("Willem pays for all including himself") {
            // Given...
            val willem = Person("Willem Wever", 34, "willem.wever@loveskotlin.nl")
            val henk = Person("Henk the Tank", 54, "henkietenkie@kotlindevday.nl")
            val jan = Person("Jan van de Niltok", 23, "janvandeniltok@kotlinrocks.nl")

            // When...
            val settleService = PaymentSettlementService()
            settleService.settlePayment(
                paidBy = willem,
                paidFor = listOf(willem, henk, jan),
                totalAmount = 90f
            )

            // Then...
            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }
            sumOfBalances shouldBe 0f
        }

        scenario("Willem pays for all excluding himself") {
            val willem = Person("Willem Wever", 34, "willem.wever@loveskotlin.nl")
            val henk = Person("Henk the Tank", 54, "henkietenkie@kotlindevday.nl")
            val jan = Person("Jan van de Niltok", 23, "janvandeniltok@kotlinrocks.nl")

            val settleService = PaymentSettlementService()
            settleService.settlePayment(
                paidBy = willem,
                paidFor = listOf(henk, jan),
                totalAmount = 90f
            )

            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0f
        }

        scenario("Willem pays for all excluding himself") {
            val willem = Person("Willem Wever", 34, "willem.wever@loveskotlin.nl")
            val henk = Person("Henk the Tank", 54, "henkietenkie@kotlindevday.nl")
            val jan = Person("Jan van de Niltok", 23, "janvandeniltok@kotlinrocks.nl")

            val settleService = PaymentSettlementService()
            settleService.settlePayment(
                paidBy = willem,
                paidFor = listOf(henk, jan),
                totalAmount = 90f
            )

            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0f
        }

        scenario("Willem pays twice for all excluding himself") {
            val willem = Person("Willem Wever", 34, "willem.wever@loveskotlin.nl")
            val henk = Person("Henk the Tank", 54, "henkietenkie@kotlindevday.nl")
            val jan = Person("Jan van de Niltok", 23, "janvandeniltok@kotlinrocks.nl")

            val settleService = PaymentSettlementService()
            settleService.settlePayment(
                paidBy = willem,
                paidFor = listOf(henk, jan),
                totalAmount = 90f
            )

            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0f
        }

        scenario("Willem pays twice for all including himself") {
            val willem = Person("Willem Wever", 34, "willem.wever@loveskotlin.nl")
            val henk = Person("Henk the Tank", 54, "henkietenkie@kotlindevday.nl")
            val jan = Person("Jan van de Niltok", 23, "janvandeniltok@kotlinrocks.nl")

            val settleService = PaymentSettlementService()
            settleService.settlePayment(
                paidBy = willem,
                paidFor = listOf(henk, jan),
                totalAmount = 90f
            )

            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0f
        }
    }
})