package com.api.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Common disposable function
 */
fun Disposable.disposedBy(compositeDisposable: CompositeDisposable): Disposable {
    compositeDisposable.add(this)

    return this
}

/**
 * Int number to comma separated string
 */
fun Int?.toCommaSeparate(): String {
    if (this == null)
        return ""
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###,###,###")
    return formatter.format(this)
}

/*change to yyyy-mm-dd to dd/mmm/yyyy */
fun String?.toDateFormat(): String {
    if (this == null || this.isEmpty())
        return ""
    val inputFormat = SimpleDateFormat("yyyy-mm-dd", Locale.US)
    val outputFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.US)
    return outputFormat.format(inputFormat.parse(this)!!)
}