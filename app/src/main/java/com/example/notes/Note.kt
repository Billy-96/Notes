package com.example.notes

import java.io.Serializable
import java.util.*

data class Note(
    var title: String,
    var body: String,
    var time: Date,
    var readen: Boolean
): Serializable