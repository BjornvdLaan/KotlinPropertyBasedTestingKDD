package org.kdd.pbt

data class Person(val name: String, val age: Int, val email: String)

typealias Euro = Float

class PaymentSettlementService {
    val balances: MutableMap<Person, Euro> = mutableMapOf()

    fun settlePayment(paidBy: Person, paidFor: List<Person>, totalAmount: Euro) {
        val totalMembers = paidFor.size
        val amountPerMember = totalAmount / totalMembers

        balances[paidBy] = balances.getOrDefault(paidBy, 0.0f) + totalAmount

        paidFor.forEach { member ->
            balances[member] = balances.getOrDefault(member, 0.0f) - amountPerMember
        }
    }
}