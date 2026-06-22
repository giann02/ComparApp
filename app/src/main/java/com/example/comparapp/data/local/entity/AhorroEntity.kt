package com.example.comparapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ahorros")
data class AhorroEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val origen: String,
    val destino: String,
    val ahorro: Int,
    val fecha: Long = System.currentTimeMillis()
)
