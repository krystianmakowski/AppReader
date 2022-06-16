package com.makowski.appreader.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp

fun formatDate(timestamp: Timestamp): String {
    return DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString().split(",")[0]
}