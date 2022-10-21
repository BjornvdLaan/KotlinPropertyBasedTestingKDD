package org.nljug.pbt

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.property.Arb
import io.kotest.property.Shrinker
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.arbs.firstName
import io.kotest.property.checkAll

class EuroPropertyBasedTest : FeatureSpec({
    infix fun Euro.shouldBe(other: Euro) =
        this.toCents() shouldBeExactly other.toCents()

    feature("Sum of all balances should always be zero") {
        checkAll(
            Arb.positiveInt(), Arb.list(Arb.firstName(), 2..4)
        ) { totalAmount, firstNames ->
            val payees = firstNames.map { it.name }
            val payer = payees.random()

            val settleService = PaymentSettlementService()
            settleService.settlePayment(payer, payees, Euro.fromCents(totalAmount))

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }
    }

    val euroEdgeCases = listOf(
        Euro.fromCents(0)
    )

    val euroShrinker = Shrinker<Euro> { euro ->
        listOf(
            Euro.fromCents(euro.toCents() - 1),
            Euro.fromCents(euro.toCents() - 100)
        )
    }

    fun Arb.Companion.euro() = arbitrary(edgecases = euroEdgeCases, shrinker = euroShrinker) {
        val cents = Arb.positiveInt().bind()
        Euro.fromCents(cents)
    }

    feature("Sum of all balances should always be zero (with custom shrinking arb)") {
        checkAll(
            Arb.euro(), Arb.list(Arb.firstName(), 2..5)
        ) { totalAmount, firstNames ->
            val payees = firstNames.map { it.name }
            val payer = payees.random()

            val settleService = PaymentSettlementService()
            settleService.settlePayment(payer, payees, totalAmount)

            val sumOfBalances = settleService.balances.values
                .fold(Euro(0)) { sum, balance -> sum + balance }

            sumOfBalances shouldBe Euro(0)
        }
    }
})