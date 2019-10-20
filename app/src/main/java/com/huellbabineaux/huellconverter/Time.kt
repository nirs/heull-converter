package com.huellbabineaux.huellconverter

const val HOURS_PER_HUELL = 1.71666667

enum class Units {
    SECONDS, MINUTES, HOURS, HUELLS
}

class Interval(value: Double, unit: Units = Units.SECONDS) {

    constructor(value: String, unit: Units = Units.SECONDS) : this(value.toDouble(), unit)

    val seconds = when(unit) {
        Units.HUELLS -> value * HOURS_PER_HUELL * 3600
        Units.HOURS -> value * 3600
        Units.MINUTES -> value * 60
        Units.SECONDS -> value
    }

    fun toUnit(unit: Units) : Double = when(unit) {
        Units.HOURS -> seconds / 3600
        Units.MINUTES -> seconds / 60
        Units.SECONDS -> seconds
        Units.HUELLS -> seconds / 3600 / HOURS_PER_HUELL
    }

    override fun toString() : String = seconds.toString()
}