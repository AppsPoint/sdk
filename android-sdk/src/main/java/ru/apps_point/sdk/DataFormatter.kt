package ru.apps_point.sdk

interface DataFormatter<Input, Output> {

    fun format(input: Input): Output
}