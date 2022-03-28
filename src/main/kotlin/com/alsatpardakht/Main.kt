package com.alsatpardakht

import com.alsatpardakht.alsatipgcore.domain.model.PaymentType
import com.alsatpardakht.alsatipgdesktop.AlsatIPG
import java.awt.Desktop
import java.lang.Thread.sleep
import java.net.URI

val API = "ENTER YOUR API KEY HERE"
val alsatIPG = AlsatIPG.getInstance()
val paymentType = PaymentType.Mostaghim

fun main() {
    testSign()
    sleep(600_000)// in order to keep the console alive
    removeObservers()
}

fun testSign() {
    alsatIPG.paymentSignStatus.observeForever { paymentSignResult ->
        when {
            paymentSignResult.isSuccessful -> {
                println("successful")
                println(paymentSignResult)

                try {
                    if (Desktop.isDesktopSupported() &&
                        Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)
                    ) {
                        Desktop.getDesktop().browse(URI(paymentSignResult.url!!))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                println(">> validation <<")
                print("enter tref : ")
                val tref = readln()
                print("enter iN : ")
                val iN = readln()
                print("enter iD : ")
                val iD = readln()
                print("enter PayId : ")
                val PayId = readln()

                testValidation(tref = tref, iN = iN, iD = iD, PayId = PayId)
                readln()
            }
            paymentSignResult.isLoading -> {
                println("loading")
            }
            else -> {
                println("error : " + paymentSignResult.error?.message)
            }
        }
    }
    when (paymentType) {
        PaymentType.Mostaghim -> alsatIPG.signMostaghim(
            Amount = 10_000,
            Api = API,
            InvoiceNumber = "123",
            RedirectAddress = "http://eample.com"
        )
        PaymentType.Vaset -> alsatIPG.signVaset(
            Amount = 20_000,
            Api = API,
            RedirectAddress = "http://eample.com",
            Tashim = emptyList(),
            InvoiceNumber = "123"
        )
    }
}

fun testValidation(tref: String, iN: String, iD: String, PayId: String) {
    alsatIPG.paymentValidationStatus.observeForever { paymentValidationResult ->
        when {
            paymentValidationResult.isSuccessful -> {
                println("successful")
                println("payment Validation Success data = ${paymentValidationResult.data}")
                if (
                    (paymentValidationResult.data?.PSP?.IsSuccess == true) &&
                    (paymentValidationResult.data?.VERIFY?.IsSuccess == true)
                ) {
                    println("money transferred")
                } else {
                    println("money has not been transferred")
                }
            }
            paymentValidationResult.isLoading -> {
                println("loading")
            }
            else -> {
                println("error : " + paymentValidationResult.error?.message)
            }
        }
    }
//  val data = URI("http://eample.com/?tref=637829347727645295&iN=123&iD=2022/03/15%2009:52:54")
    when (paymentType) {
        PaymentType.Mostaghim -> alsatIPG.validationMostaghim(
            Api = API,
//          data = data
            tref = tref,
            iN = iN,
            iD = iD,
            PayId = PayId
        )
        PaymentType.Vaset -> alsatIPG.validationVaset(
            Api = API,
//          data = data
            tref = tref,
            iN = iN,
            iD = iD,
            PayId = PayId
        )
    }
}

fun removeObservers() {
    alsatIPG.paymentSignStatus.removeAllObservers()
    alsatIPG.paymentValidationStatus.removeAllObservers()
}