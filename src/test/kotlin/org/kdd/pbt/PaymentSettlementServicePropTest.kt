package org.kdd.pbt

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.arbs.firstName
import io.kotest.property.checkAll

class PaymentSettlementServicePropTest : FeatureSpec({
    feature("Sum of all balances should always be zero") {
        checkAll(
            Arb.numericFloat(min = 0f, max = 100f), Arb.Companion.int(2..4)
        ) { totalAmount, numberOfPersons ->

            val roundedAmount = String.format("%.2f", totalAmount).toFloat()
            val persons = (1..numberOfPersons)
                .map {
                    Person(
                        name = Arb.firstName().next().toString(),
                        age = Arb.positiveInt(max = 92).next(),
                        email = Arb.email().next()
                    )
                }

            val settleService = PaymentSettlementService()
            settleService.settlePayment(persons.random(), persons, roundedAmount)

            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0f

        }
    }

    fun Arb.Companion.person() = Arb.bind(Arb.firstName(), Arb.positiveInt(92), Arb.email()) { firstName, age, email ->
        Person(firstName.toString(), age, email)
    }

    fun Arb.Companion.moneyAmount() = arbitrary(shrinker = { amount -> listOf(amount - 1.00f, amount + 1.00f) }) {
        val amount = Arb.numericFloat(min = 0f, max = 100f).bind()
        String.format("%.2f", amount).toFloat()
    }

    feature("Sum of all balances should always be zero (custom arb)") {
        checkAll(
            Arb.moneyAmount(), Arb.list(Arb.person(), 2..4)
        ) { totalAmount, persons ->
            val settleService = PaymentSettlementService()
            settleService.settlePayment(persons.random(), persons, totalAmount)

            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0.0f
        }
    }
})