package com.dheeraj.hilt.daggerhilt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blogs")
data class Blog(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val pk: Int,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "title")
    val title: String
)