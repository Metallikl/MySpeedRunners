package com.dluche.myspeedrunners.domain.model.runner

enum class NameStyleEnum(val style: String) {
    GRADIENT("gradient"),
    SOLID("solid"),
    UNKNOWN("unknown");

    companion object{
        fun parse(value: String): NameStyleEnum {
            return NameStyleEnum.entries.find { it.style == value.lowercase() } ?: UNKNOWN
        }
    }

}