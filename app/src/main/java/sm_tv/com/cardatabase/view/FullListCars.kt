package sm_tv.com.cardatabase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
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
    private lateinit var adapterSpinner: ArrayAdapter<String>
    private lateinit var mySpinner: Spinner
    private lateinit var myEdTextSort: TextView
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var carList: List<CarData>
    private lateinit var brandCarList: List<String>
    private lateinit var filterCarLIst: List<CarData>
    private var filterFlag = false
    private var paramSortSpinner = ""
    private val testList = listOf<String>("Лада", "BMV", "Ford")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_list_cars, container, false)
        init(view)
        lookAfterAllDataCar()
        lookAfterBrandCar()
        super.onStart()
        customRecycler(view, adapter)
        addNewCar(view)
        //initSpinner(view, brandCarList)
        filterByYears(view)
        return view
    }

    override fun onStart() {
        super.onStart()

    }

    private fun init(view: View) {
        mViewModel = ViewModelProvider(this)[CarDataViewModel::class.java]
        myEdTextSort = view.findViewById(R.id.myEdTextSort)
        adapter = NewAdapter()
        myRecyclerView = view.findViewById(R.id.myRecycler)
        mySpinner = view.findViewById<Spinner>(R.id.spinner)
    }

    private fun lookAfterAllDataCar() {
        mViewModel.readAllData.observe(viewLifecycleOwner, Observer { car ->
            carList = car
            updateData(carList)
        })
    }

    private fun lookAfterBrandCar() {
        mViewModel.readBrandCar.observe(viewLifecycleOwner, Observer { brand ->
            brandCarList = brand.distinct()
            initSpinner()
        })
    }

    private fun addNewCar(view: View) {
        val buttonAddNewCar = view.findViewById<FloatingActionButton>(R.id.idAddNewCar)
        buttonAddNewCar.setOnClickListener {
            findNavController().navigate(R.id.action_fullListCars_to_parameterSettingScreen)
        }
    }

    private fun sortByName(sortParam: String) {
        if (filterFlag) {
            adapter.getParamSort(sortParam)
            updateData(filterCarLIst)
        } else {
            adapter.getParamSort(sortParam)
            updateData(carList)
        }
    }

    private fun filterByYears(view: View) {
        val myEdYearsFilter = view.findViewById<EditText>(R.id.myEdYearsFilter)
        val myCheckBox = view.findViewById<CheckBox>(R.id.myCheckBox)
        myCheckBox.setOnCheckedChangeListener { compoundButton, checked ->
            filterFlag = checked
            if (checked) {
                if (myEdYearsFilter.text.toString() != "") {
                    filterCarLIst = carList.filter { it.yearIssue == myEdYearsFilter.text.toString().toInt() }
                    adapter.getParamFilter(checked)
                    updateData(filterCarLIst)
                }
            } else {
                adapter.getParamFilter(false)
                updateData(carList)
            }
        }
    }

    private fun updateData(carData: List<CarData>) {
        adapter.setItems(carData)
        myRecyclerView.smoothScrollToPosition(0)
    }

    private fun customRecycler(view: View, adapter: NewAdapter) {
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myRecyclerView.adapter = adapter
        myRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(myRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val deleteButton = deleteButton(position)
                val updateButton = updateButton(position)
                return listOf(deleteButton, updateButton)
            }
        })
        itemTouchHelper.attachToRecyclerView(myRecyclerView)
    }

    private fun initSpinner() {
        adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brandCarList)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapterSpinner
        mySpinner.prompt = "Параметр сортировки"
        mySpinner.setSelection(0)
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                paramSortSpinner = mySpinner.selectedItem.toString()
                sortByName(paramSortSpinner)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //mySpinner.setSelection(0)
            }
        }

    }


    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    val car = adapter.sortedList.get(position)
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
                    val carData = adapter.sortedList.get(position)
                    val action = FullListCarsDirections.actionFullListCarsToUpdateCarFragment(carData)
                    findNavController().navigate(action)
                }
            })
    }
}
