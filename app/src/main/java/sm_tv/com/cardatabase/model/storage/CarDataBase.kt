package sm_tv.com.cardatabase.model.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sm_tv.com.cardatabase.model.CarData

@Database(entities = [CarData::class], version = 1)
abstract class CarDataBase: RoomDatabase() {

    abstract fun carDao(): CarDao

    companion object {
        @Volatile
        private var INSTANCE: CarDataBase? = null

        fun getDatabase(context: Context): CarDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDataBase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }




}