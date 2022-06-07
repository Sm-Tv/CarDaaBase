package sm_tv.com.cardatabase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.model.storage.CarDataBase
import sm_tv.com.cardatabase.model.storage.repositories.CarDataRepository

class CarDataViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<CarData>>

    //val readAllDataCompletedNote: LiveData<List<Note>>
    private val repository: CarDataRepository

    init {
        val carDao = CarDataBase.getDatabase(application).carDao()
        repository = CarDataRepository(carDao)
        readAllData = repository.readAllData
    }

    fun addCarData(carData: CarData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCarData(carData)
        }
    }

    fun updateCarData(carData: CarData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateCarData(carData)
        }
    }

    fun deleteCarData(carData: CarData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCarData(carData)
        }
    }
}