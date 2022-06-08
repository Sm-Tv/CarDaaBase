package sm_tv.com.cardatabase.model.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import sm_tv.com.cardatabase.model.CarData

@Dao
interface CarDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (carData: CarData)

    @Update
    suspend fun update(carData: CarData)

    @Delete
    suspend fun delete(carData: CarData)

    @Query("SELECT * FROM CarData ORDER BY uid DESC")
    fun getAllLiveData(): LiveData<List<CarData>>

    @Query("SELECT Name  FROM CarData")
    fun getBrandCar(): LiveData<List<String>>
}