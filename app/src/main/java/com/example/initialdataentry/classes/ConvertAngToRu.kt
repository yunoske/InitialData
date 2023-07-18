package com.example.initialdataentry.classes

import java.lang.StringBuilder

class ConvertAngToRu {
    fun convert(string: String): String {
        val stringBuilder = StringBuilder()

        for (char in string) {
            when (char) {
                'A' -> stringBuilder.append("А")
                'B' -> stringBuilder.append("В")
                'C' -> stringBuilder.append("С")
                'E' -> stringBuilder.append("Е")
                'H' -> stringBuilder.append("Н")
                'K' -> stringBuilder.append("К")
                'M' -> stringBuilder.append("М")
                'O' -> stringBuilder.append("О")
                'P' -> stringBuilder.append("Р")
                'T' -> stringBuilder.append("Т")
                'X' -> stringBuilder.append("Х")
                'Y' -> stringBuilder.append("У")
                else -> stringBuilder.append(char)
            }
        }

        return stringBuilder.toString()
    }
}