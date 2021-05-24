package com.example.notes

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

data class Note(
    var title: String,
    var body: String,
    var time: String,
    var id : String
): Serializable