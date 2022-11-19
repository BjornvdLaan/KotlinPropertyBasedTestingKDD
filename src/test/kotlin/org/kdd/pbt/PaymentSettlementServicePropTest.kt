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
            // Given some generated arbitrary values...
            Arb.numericFloat(min = 0f, max = 100f), Arb.nonNegativeInt(4)
        ) { totalAmount, numberOfPersons ->
            // (transform generated values)
            val roundedAmount = String.format("%.2f", totalAmount).toFloat()
            val persons = (1..numberOfPersons)
                .map {
                    Person(
                        name = Arb.firstName().next().toString(),
                        age = Arb.positiveInt(max = 92).next(),
                        email = Arb.email().next()
                    )
                }

            // When we call the method...
            val settleService = PaymentSettlementService()
            settleService.settlePayment(persons.random(), persons, roundedAmount)

            // Then we expect certain stuff...
            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }
            sumOfBalances shouldBe 0f
        }
    }

    fun Arb.Companion.person(): Arb<Person> =
        arbitrary(
            shrinker = { person -> listOf(
                person.copy(age = person.age - 1),
                person.copy(age = person.age + 1),
                person.copy(name = person.name.drop(1)),
                person.copy(name = person.name.dropLast(1))
            )}
        ) {
            val firstName = Arb.firstName().bind()
            val age = Arb.positiveInt().bind()
            val email = Arb.email().bind()
            Person(firstName.toString(), age, email)
        }

    fun Arb.Companion.moneyAmount(): Arb<Euro> =
        arbitrary(shrinker = { amount -> listOf(amount - 1.00f, amount + 1.00f) }) {
            val amount = Arb.numericFloat(min = 0f, max = 100f).bind()
            String.format("%.2f", amount).toFloat()
        }

    feature("Sum of all balances should always be zero (custom arb)") {
        checkAll(
            // Given some generated arbitrary values...
            Arb.moneyAmount(), Arb.list(Arb.person(), 0..4)
        ) { totalAmount, persons ->
            // When we call the method...
            val settleService = PaymentSettlementService()
            settleService.settlePayment(persons.random(), persons, totalAmount)

            // Then we expect certain stuff...
            val sumOfBalances = settleService.balances.values
                .reduce { sum, balance -> sum + balance }

            sumOfBalances shouldBe 0.0f
        }
    }
})