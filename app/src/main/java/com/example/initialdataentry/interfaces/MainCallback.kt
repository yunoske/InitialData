package com.example.initialdataentry.interfaces

interface MainCallback {
    fun carNumButtonClicked(boolean: Boolean): Boolean
    fun containsEditText(boolean: Boolean)
    fun carNumberTransfer(number: String)
    fun carNumberTransfer(): String
}