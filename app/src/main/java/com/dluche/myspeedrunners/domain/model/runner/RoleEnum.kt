package com.dluche.myspeedrunners.domain.model.runner

enum class RoleEnum {
    BANNED,
    USER,
    TRUSTED,
    MODERATOR,
    ADMIN,
    PROGRAMMER,
    GUEST,

    UNKNOWN;

    companion object {
        fun fromString(role: String?): RoleEnum {
            return RoleEnum.entries.find {
                it.name.lowercase() == role?.lowercase().orEmpty()
            } ?: UNKNOWN
        }
    }
}