package com.example.noticeservice.shared.helper

object EnumHelper {
    inline fun <reified T : Enum<T>> getAllNamesAsString(separator: String = ", "): String =
        enumValues<T>().joinToString(separator) { it.name }
}
