package sm_tv.com.cardatabase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.model.adapter.NewAdapter
import sm_tv.com.cardatabase.utils.SwipeHelper
import sm_tv.com.cardatabase.viewModel.CarDataViewModel


class FullListCars : Fragment() {

    private lateinit var mViewModel: CarDataViewModel
    private lateinit var adapter: NewAdapter
    private lateinit var holder: NewAdapter.MyViewHolder
    private lateinit var carList: List<CarData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_list_cars, container, false)
        initMyVM()
        adapter = NewAdapter()
        holder = NewAdapter.MyViewHolder(view)
        initRecycler(view, adapter)
        addNewCar(view)
        sortByName(view)


        return view
    }

    private fun initMyVM() {
        mViewModel = ViewModelProvider(this)[CarDataViewModel::class.java]
        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { car ->
            carList = car
            adapter.setItems(car, "amv")
        })
    }

    private fun addNewCar(view: View) {
        val buttonAddNewCar = view.findViewById<FloatingActionButton>(R.id.idAddNewCar)
        buttonAddNewCar.setOnClickListener {
            findNavController().navigate(R.id.action_fullListCars_to_parameterSettingScreen)
        }
    }

    private fun sortByName(view: View) {
        val textName = view.findViewById<EditText>(R.id.myEdTextSort)
        val buttonSort = view.findViewById<Button>(R.id.myButtonSort)
        buttonSort.setOnClickListener {
            adapter.setItems(carList, textName.text.toString())
        }
    }

    private fun initRecycler(view: View, adapter: NewAdapter) {
        val myRecyclerView = view.findViewById<RecyclerView>(R.id.myRecycler)
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myRecyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(myRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val deleteButton = deleteButton(position)
                val updateButton = updateButton(position)
                return listOf(deleteButton, updateButton)
            }
        })
        itemTouchHelper.attachToRecyclerView(myRecyclerView)
    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    println("________________${carList[position]}")
                    val size = carList.size
                    val car = carList[(size - 1) - position]
                    mViewModel.deleteCarData(car)
                }
            })
    }

    private fun updateButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Update",
            14.0f,
            android.R.color.holo_blue_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    val size = carList.size
                    val carData = carList[(size - 1) - position]
                    val action = FullListCarsDirections.actionFullListCarsToUpdateCarFragment(carData)
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "updateButton", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
