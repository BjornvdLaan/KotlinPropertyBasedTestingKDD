package org.kdd.pbt

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class PaymentSettlementServiceParaTest : FeatureSpec({
    feature("Sum of all balances should always be zero") {
        val willem = Person("Willem Wever", 34, "willem.wever@loveskotlin.nl")
        val henk = Person("Henk the Tank", 54, "henkietenkie@kotlindevday.nl")
        val jan = Person("Jan van de Niltok", 23, "janvandeniltok@kotlinrocks.nl")

        data class PaymentTestCase(val amount: Float, val paidBy: Person, val paidFor: List<Person>)
        val testCases = listOf(
            PaymentTestCase(amount = 90f, paidBy = willem, paidFor = listOf(willem, henk, jan)),
            PaymentTestCase(amount = 90f, paidBy = willem, paidFor = listOf(henk, jan)),
            PaymentTestCase(amount = 0f, paidBy = willem, paidFor = listOf(willem, henk, jan)),
            PaymentTestCase(amount = -90f, paidBy = willem, paidFor = listOf(willem, henk, jan)),
            /* and more examples... */
        )

        withData(testCases) { (totalAmount, paidBy, paidFor) ->
            // When...
            val settleService = PaymentSettlementService()
            settleService.settlePayment(paidBy, paidFor, totalAmount)

            // Then...
            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }
            sumOfBalances shouldBe 0f
        }
    }
})