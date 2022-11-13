package org.kdd.pbt

data class Person(val name: String, val age: Int, val email: String)

typealias Euro = Float

class PaymentSettlementService {
    val balances: MutableMap<Person, Euro> = mutableMapOf()

    fun settlePayment(paidBy: Person, paidFor: List<Person>, totalAmount: Euro) {
        // 1. Increase balance of person who paid
        balances[paidBy] = balances.getOrDefault(paidBy, 0.0f) + totalAmount

        // 2. Calculate amount per person
        val amountPerPerson = totalAmount / paidFor.size

        // 3. Decrease balance of persons who were paid for
        paidFor.forEach { member ->
            balances[member] = balances.getOrDefault(member, 0.0f) - amountPerPerson
        }
    }
}