package org.nljug.pbt

class PaymentSettlementService {
    val balances: MutableMap<String, Euro> = mutableMapOf()

    fun settlePayment(paidBy: String, paidFor: List<String>, totalAmount: Euro) {
        val totalMembers = paidFor.size
        val amountPerMember = totalAmount / totalMembers

        balances[paidBy] = balances.getOrDefault(paidBy, Euro(0)) + totalAmount

        paidFor.forEach { member ->
            balances[member] = balances.getOrDefault(member, Euro(0)) - amountPerMember
        }
    }
}