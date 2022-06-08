package sm_tv.com.cardatabase.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import sm_tv.com.cardatabase.R

class FullScreenCarImage : Fragment() {
    private val args by navArgs<FullScreenCarImageArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_full_screen_car_image, container, false)
        val myImFullScreen = view.findViewById<ImageView>(R.id.imFulScreen)
        val imageUri = Uri.parse(args.url)
        Picasso.get().load(imageUri).error(R.drawable.ic_baseline_directions_car_24).into(myImFullScreen)
        return view
    }
}
