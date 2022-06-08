package sm_tv.com.cardatabase.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.constans.Constants.PICK_IMAGE_MULTIPLE_REQUEST_CODE
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.viewModel.CarDataViewModel
import java.io.FileNotFoundException

class AddCarFragment : Fragment() {

    private lateinit var mViewModel: CarDataViewModel
    private lateinit var edMyName: EditText
    private lateinit var edMyYears: EditText
    private lateinit var myImageCar: ImageView
    private lateinit var edMyClassification: EditText
    private lateinit var myButtonAdd: Button
    private var imageUri: Uri? = Uri.parse("uri")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_car_fragment, container, false)
        initElement(view)
        insertNewCar()
        addImageCar()
        return view
    }

    private fun initElement(view: View) {
        mViewModel = ViewModelProvider(this)[CarDataViewModel::class.java]
        myButtonAdd = view.findViewById(R.id.myButtonAdd)
        edMyName = view.findViewById(R.id.myEdName)
        edMyYears = view.findViewById(R.id.myEdYears)
        edMyClassification = view.findViewById(R.id.myEdClassification)
        myImageCar = view.findViewById<ImageView>(R.id.myImViewNewCar)
    }

    private fun insertNewCar() {
        myButtonAdd.setOnClickListener {
            val url = imageUri.toString()
            val name = edMyName.text.toString()
            val years = if (edMyYears.text.toString() == "") {
                0
            } else {
                edMyYears.text.toString().toInt()
            }
            val classification = edMyClassification.text.toString()
            val timestamp = System.currentTimeMillis()
            if (chekInput(name) && chekInput(years) && chekInput(classification)) {
                val carData = CarData(0, url, name, years, classification, timestamp)
                mViewModel.addCarData(carData)
                findNavController().navigate(R.id.action_parameterSettingScreen_to_fullListCars)
            } else {
                Toast.makeText(requireContext(), resources.getString(R.string.warning_message_shop), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addImageCar() {
        myImageCar.setOnClickListener {
            callPickedImageActivity()
        }
    }

    private fun chekInput(title: Any): Boolean {
        return !(TextUtils.isEmpty(title.toString()))
    }

    private fun callPickedImageActivity() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_MULTIPLE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            PICK_IMAGE_MULTIPLE_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                try {
                    imageUri = imageReturnedIntent?.data
                    Picasso.get().load(imageUri).into(myImageCar)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
