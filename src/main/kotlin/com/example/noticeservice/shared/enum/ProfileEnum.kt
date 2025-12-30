package com.example.noticeservice.shared.enum

enum class ProfileEnum(
    val value: String,
) {
    PROD("prod"),
    DEV("dev"),
    UNKNOWN("unknown"),
    ;

    fun isUnknown() = this == UNKNOWN

    companion object {
        private val m = entries.associateBy(ProfileEnum::value)

        fun getEnumByValue(value: String?) = m[value] ?: UNKNOWN
    }
}
