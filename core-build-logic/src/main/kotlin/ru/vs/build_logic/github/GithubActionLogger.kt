package ru.vs.build_logic.github

object GithubActionLogger {
    fun w(message: String) {
        println("::warning::$message")
    }
}
