package sm_tv.com.cardatabase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import sm_tv.com.cardatabase.R

class FullScreenCarImage : Fragment() {
    //private val args by navArgs<FullScreenCarImageArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_screen_car_image, container, false)
    }
}
