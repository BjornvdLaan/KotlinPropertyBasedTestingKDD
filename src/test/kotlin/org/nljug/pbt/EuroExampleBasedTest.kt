package org.nljug.pbt

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.shouldBeExactly

class EuroExampleBasedTest : FeatureSpec({
    infix fun Euro.shouldBe(other: Euro) =
        this.toCents() shouldBeExactly other.toCents()

    feature("Sum of all balances should always be zero") {
        scenario("Willem pays for all including himself") {
            val payees = listOf("Willem", "Henk", "Jan")
            val payer = "Willem"

            val settleService = PaymentSettlementService()
            settleService.settlePayment(payer, payees, Euro.fromCents(90))

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }

        scenario("Willem pays for all excluding himself") {
            val payees = listOf("Henk", "Jan")
            val payer = "Willem"

            val settleService = PaymentSettlementService()
            settleService.settlePayment(payer, payees, Euro.fromCents(90))

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }

        scenario("Willem pays a negative amount for all") {
            val payees = listOf("Willem", "Henk", "Jan")
            val payer = "Willem"

            val settleService = PaymentSettlementService()
            settleService.settlePayment(payer, payees, Euro.fromCents(-90))

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }

        scenario("Willem pays a big amount for all") {
            val payees = listOf("Willem", "Henk", "Jan")
            val payer = "Willem"

            val settleService = PaymentSettlementService()
            settleService.settlePayment(payer, payees, Euro.fromCents(30942))

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }

        scenario("Willem and Henk both pay something for the group") {
            val payees = listOf("Willem", "Henk", "Jan")

            val settleService = PaymentSettlementService()
            settleService.settlePayment("Willem", payees, Euro.fromCents(30))
            settleService.settlePayment("Henk", payees, Euro.fromCents(30))

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }
    }
})