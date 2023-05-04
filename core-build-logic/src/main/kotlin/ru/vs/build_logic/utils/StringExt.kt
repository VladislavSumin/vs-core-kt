package ru.vs.build_logic.utils

fun String.snakeToCamelCase(): String {
    return replace(SNAKE_TO_CAMEL_REGEX) { it.value.last().uppercase() }
}

private val SNAKE_TO_CAMEL_REGEX = "_[a-z]".toRegex()