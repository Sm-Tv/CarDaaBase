package sm_tv.com.cardatabase.view

import android.app.Activity
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
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.constans.Constants
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.viewModel.CarDataViewModel
import java.io.FileNotFoundException

class UpdateCarFragment : Fragment() {

    private val args by navArgs<UpdateCarFragmentArgs>()

    private lateinit var mViewModel: CarDataViewModel
    private lateinit var edMyNameUp: EditText
    private lateinit var edMyYearsUp: EditText
    private lateinit var edMyClassificationUp: EditText
    private lateinit var myButtonAddUp: Button
    private lateinit var myImCarUp: ImageView
    private var imageUri: Uri? = Uri.parse("uri")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_car, container, false)

        initElement(view)
        Picasso.get().load(Uri.parse(args.carItem.url))
            .error(R.drawable.ic_baseline_directions_car_24).into(myImCarUp)
        dataProtection()
        updateImageCar()
        updateItem()
        return view
    }

    private fun initElement(view: View) {
        myImCarUp = view.findViewById(R.id.myImCarUp)
        mViewModel = ViewModelProvider(this)[CarDataViewModel::class.java]
        myButtonAddUp = view.findViewById(R.id.myButtonAddUp)
        edMyNameUp = view.findViewById(R.id.myEdNameUp)
        edMyYearsUp = view.findViewById(R.id.myEdYearsUp)
        edMyClassificationUp = view.findViewById(R.id.myEdClassificationUp)
    }

    private fun dataProtection() {
        edMyNameUp.setText(args.carItem.name)
        edMyYearsUp.setText(args.carItem.yearIssue.toString())
        edMyClassificationUp.setText(args.carItem.classification)
    }

    private fun updateItem() {
        myButtonAddUp.setOnClickListener {
            if (imageUri == Uri.parse("uri")) {
                imageUri = Uri.parse(args.carItem.url)
            }
            val url = imageUri.toString()
            val name = edMyNameUp.text.toString()
            val years = if (edMyYearsUp.text.toString() == "") {
                0
            } else {
                edMyYearsUp.text.toString().toInt()
            }
            val classification = edMyClassificationUp.text.toString()
            if (chekInput(url) && chekInput(name) && chekInput(years) && chekInput(classification)) {
                val carData = CarData(
                    args.carItem.uid,
                    url,
                    name,
                    years,
                    classification,
                    args.carItem.timestamp
                )
                mViewModel.updateCarData(carData)
                findNavController().navigate(R.id.action_updateCarFragment_to_fullListCars)
            } else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.warning_message_shop),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun updateImageCar() {
        myImCarUp.setOnClickListener {
            callPickedImageActivity()
        }
    }

    private fun chekInput(title: Any): Boolean {
        return !(TextUtils.isEmpty(title.toString()))
    }

    private fun callPickedImageActivity() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, Constants.PICK_IMAGE_MULTIPLE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            Constants.PICK_IMAGE_MULTIPLE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                try {
                    imageUri = imageReturnedIntent?.data
                    Picasso.get().load(imageUri).error(R.drawable.ic_baseline_directions_car_24)
                        .into(myImCarUp)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }


}
