package com.example.thmanyahaudiotask.mvi_base

interface Intent<T> {
    fun reduce(oldState: T): T
}

inline fun <T> intent(crossinline block: T.() -> T): Intent<T> = object : Intent<T> {
    override fun reduce(oldState: T): T = block(oldState)
}

inline fun <T> sideEffect(crossinline block: T.() -> Unit): Intent<T> = object : Intent<T> {
    override fun reduce(oldState: T): T = oldState.apply(block)
}
