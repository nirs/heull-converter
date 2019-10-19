package com.huellbabineaux.huellconverter

const val HOURS_PER_HUELL = 1.71666667

enum class Units {
    HOURS, MINUTES, SECONDS, HUELLS
}

fun secondsFrom(value: Double, unit: Units) = when(unit) {
    Units.HUELLS -> value * HOURS_PER_HUELL * 3600
    Units.HOURS -> value * 3600
    Units.MINUTES -> value * 60
    Units.SECONDS -> value
}

fun secondsTo(seconds: Double, unit: Units) : Double = when(unit) {
    Units.HOURS -> seconds / 3600
    Units.MINUTES -> seconds / 60
    Units.SECONDS -> seconds
    Units.HUELLS -> seconds / 3600 / HOURS_PER_HUELL
}
