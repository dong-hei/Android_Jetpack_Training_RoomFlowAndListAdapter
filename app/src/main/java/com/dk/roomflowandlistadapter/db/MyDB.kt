package com.dk.roomflowandlistadapter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2)
abstract class MyDB  : RoomDatabase(){

    abstract fun userDao() : UserDao

    companion object{

        @Volatile
        private var INSTANCE : MyDB? = null

        fun getDB(context: Context) : MyDB {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDB::class.java,
                    "user_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}