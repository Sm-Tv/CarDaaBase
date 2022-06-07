package sm_tv.com.cardatabase.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.viewModel.CarDataViewModel

class UpdateCarFragment : Fragment() {

    private val args by navArgs<UpdateCarFragmentArgs>()
    private lateinit var myViewModel: CarDataViewModel

    private lateinit var mViewModel: CarDataViewModel
    private lateinit var edMyNameUp: EditText
    private lateinit var edMyYearsUp: EditText
    private lateinit var edMyClassificationUp: EditText
    private lateinit var myButtonAddUp: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_car, container, false)
        initElement(view)
        dataProtection()
        updateItem()
        return view
    }

    private fun initElement(view: View){
        mViewModel = ViewModelProvider(this).get(CarDataViewModel::class.java)
        myButtonAddUp = view.findViewById(R.id.myButtonAddUp)
        edMyNameUp = view.findViewById(R.id.myEdNameUp)
        edMyYearsUp = view.findViewById(R.id.myEdYearsUp)
        edMyClassificationUp = view.findViewById(R.id.myEdClassificationUp)
    }

    private fun dataProtection(){
        edMyNameUp.setText(args.carItem.name)
        edMyYearsUp.setText(args.carItem.yearIssue.toString())
        edMyClassificationUp.setText(args.carItem.classification)
    }

    private fun updateItem() {
        myButtonAddUp.setOnClickListener {
            val url = "url url url"
            val name = edMyNameUp.text.toString()
            val years = edMyYearsUp.text.toString().toInt()
            val classification =edMyClassificationUp.text.toString()
            if(chekInput(url) && chekInput(name) && chekInput(years) && chekInput(classification)){
                val carData = CarData(args.carItem.uid,url,name,years,classification,args.carItem.timestamp)
                myViewModel.updateCarData(carData)
                findNavController().navigate(R.id.action_updateCarFragment_to_fullListCars)
            } else {
                Toast.makeText(requireContext(), resources.getString(R.string.warning_message_shop), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun chekInput(title: Any): Boolean {
        return !(TextUtils.isEmpty(title.toString()))
    }


}
