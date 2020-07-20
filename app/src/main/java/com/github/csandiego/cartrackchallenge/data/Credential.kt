package com.github.csandiego.cartrackchallenge.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username"], unique = true)])
data class Credential(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var username: String,
    var password: String
)