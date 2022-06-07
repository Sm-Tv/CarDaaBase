package sm_tv.com.cardatabase.model.storage.repositories

import androidx.lifecycle.LiveData
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.model.storage.CarDao

class CarDataRepository(private val carDao: CarDao) {
    val readAllData: LiveData<List<CarData>> = carDao.getAllLiveData()

    suspend fun addCarData(carData: CarData){
        carDao.insert(carData)
    }

    suspend fun updateCarData(carData: CarData){
        carDao.update(carData)
    }

    suspend fun deleteCarData(carData: CarData){
        carDao.delete(carData)
    }
}