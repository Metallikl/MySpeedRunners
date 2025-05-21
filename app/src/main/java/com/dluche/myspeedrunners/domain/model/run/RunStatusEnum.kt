package com.dluche.myspeedrunners.domain.model.run

enum class RunStatusEnum {
    NEW,
    VERIFIED,
    REJECTED,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): RunStatusEnum {
            return RunStatusEnum.entries.find {
                it.name.lowercase() == value?.lowercase().orEmpty()
            } ?: UNKNOWN
        }
    }
}