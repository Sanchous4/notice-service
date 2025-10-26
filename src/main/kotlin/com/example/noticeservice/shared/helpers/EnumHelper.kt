package com.example.noticeservice.shared.helpers

object EnumHelper {
    inline fun <reified T : Enum<T>> getAllNamesAsString(separator: String = ", "): String {
        return enumValues<T>().joinToString(separator) { it.name }
    }
}
