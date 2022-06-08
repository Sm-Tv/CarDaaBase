package sm_tv.com.cardatabase.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.viewModel.CarDataViewModel

class AddCarFragment : Fragment() {

    private lateinit var mViewModel: CarDataViewModel
    private lateinit var edMyName: EditText
    private lateinit var edMyYears: EditText
    private lateinit var edMyClassification: EditText
    private lateinit var myButtonAdd: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.add_car_fragment, container, false)
        initElement(view)
        insertNewCar()
        return view
    }

    private fun initElement(view: View){
        mViewModel = ViewModelProvider(this).get(CarDataViewModel::class.java)
        myButtonAdd = view.findViewById(R.id.myButtonAdd)
        edMyName = view.findViewById(R.id.myEdName)
        edMyYears = view.findViewById(R.id.myEdYears)
        edMyClassification = view.findViewById(R.id.myEdClassification)
    }

    private fun insertNewCar(){
        myButtonAdd.setOnClickListener {
            val url = "url url url"
            val name = edMyName.text.toString()
            val years = if(edMyYears.text.toString() == ""){
                0
            }else{
                edMyYears.text.toString().toInt()
            }
            val classification =edMyClassification.text.toString()
            val timestamp = System.currentTimeMillis()
            if(chekInput(url) && chekInput(name) && chekInput(years) && chekInput(classification)){
                val carData = CarData(0,url,name,years,classification,timestamp)
                mViewModel.addCarData(carData)
                findNavController().navigate(R.id.action_parameterSettingScreen_to_fullListCars)
            }else {
                Toast.makeText(requireContext(),resources.getString(R.string.warning_message_shop), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun chekInput(title: Any): Boolean {
        return !(TextUtils.isEmpty(title.toString()))
    }
}
