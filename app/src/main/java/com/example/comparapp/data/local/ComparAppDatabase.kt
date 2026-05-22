package com.example.comparapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.comparapp.data.local.dao.RutaFavoritaDao
import com.example.comparapp.data.local.dao.UsuarioDao
import com.example.comparapp.data.local.entity.RutaFavoritaEntity
import com.example.comparapp.data.local.entity.UsuarioEntity

@Database(entities = [UsuarioEntity::class, RutaFavoritaEntity::class], version = 2, exportSchema = false)
abstract class ComparAppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun rutaFavoritaDao(): RutaFavoritaDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE rutas_favoritas ADD COLUMN usuarioId INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        fun create(context: Context): ComparAppDatabase =
            Room.databaseBuilder(context, ComparAppDatabase::class.java, "comparapp.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }
}
