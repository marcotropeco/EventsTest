package br.com.marcoaurelio.projecttest.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Converter {
    companion object {
        fun getDateTime(s: String): String? {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val netDate = Date(s.toLong() * 1000)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

        fun currencyFormatter(value: Double): String? {
            val ptBr = Locale("pt", "BR")
            return NumberFormat.getCurrencyInstance(ptBr).format(value)
        }

    }
}