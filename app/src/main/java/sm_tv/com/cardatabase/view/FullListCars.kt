package sm_tv.com.cardatabase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.model.adapter.NewAdapter
import sm_tv.com.cardatabase.test.SwipeController


class FullListCars : Fragment() {

    private lateinit var adapter: NewAdapter
    private lateinit var swipeController: SwipeController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_list_cars, container, false)
        adapter = NewAdapter()
        swipeController = SwipeController()
        initRecycler(view, adapter)


        test()
        return view
    }

    private fun initRecycler(view: View, adapter: NewAdapter) {
        val myRecyclerView = view.findViewById<RecyclerView>(R.id.myRecycler)
        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        val itemTouch = ItemTouchHelper(swipeController)
        itemTouch.attachToRecyclerView(myRecyclerView)
    }

    private fun test(){
        val list = arrayListOf<CarData>()
        val timestamp = System.currentTimeMillis()
        val bmv = CarData("pr","bmv", 100, "легковой авто",timestamp.toInt())
        val dmv = CarData("pr","dmv", 100, "легковой авто",timestamp.toInt())
        val amv = CarData("pr","amv", 100, "легковой авто",timestamp.toInt())
        list.add(bmv)
        list.add(dmv)
        list.add(amv)
        adapter.setItems(list, "amv")
    }


}