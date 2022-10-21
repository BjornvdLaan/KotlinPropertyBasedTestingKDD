package org.nljug.pbt

data class Euro(val euro: Int, val cents: Int = 0) {
    operator fun plus(other: Euro): Euro {
        val totalInCents = this.toCents() + other.toCents()
        return fromCents(totalInCents)
    }

    operator fun minus(other: Euro): Euro {
        val totalInCents = this.toCents() - other.toCents()
        return fromCents(totalInCents)
    }

    operator fun div(factor: Int): Euro {
        val totalInCents = this.toCents() / factor
        return fromCents(totalInCents)
    }

    fun toCents() = this.euro * 100 + cents

    companion object {
        fun fromCents(cents: Int) = Euro(cents / 100, cents % 100)
    }
}