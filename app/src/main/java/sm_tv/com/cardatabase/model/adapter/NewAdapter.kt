package sm_tv.com.cardatabase.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import kotlinx.android.synthetic.main.car_item.view.*
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.model.CarData
import sm_tv.com.cardatabase.utils.SwipeHelper
import sm_tv.com.cardatabase.view.FullListCarsDirections
import sm_tv.com.cardatabase.viewModel.CarDataViewModel

class NewAdapter: RecyclerView.Adapter<NewAdapter.MyViewHolder>() {
    //private var sortedList = SortedList<Note>()
    private var paramSort =""
    private var flagFilter = false

    var sortedList = SortedList(CarData::class.java, object : SortedList.Callback<CarData>() {

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        override fun areContentsTheSame(oldItem: CarData, newItem: CarData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(item1: CarData, item2: CarData): Boolean {
            return false
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun compare(o1: CarData, o2: CarData): Int {
            /*if(flagFilter && paramSort!="") {
                if(o2.yearIssue > o1.yearIssue && o2.name == paramSort && o1.name != paramSort)
                {
                    return 1
                }
            }*/
            if (flagFilter && o2.yearIssue > o1.yearIssue){
                return 1
            }
            if (o2.name == paramSort && o1.name != paramSort) {
                return 1
            }
            return  if (o2.name != paramSort && o1.name == paramSort) {
               -1
            }else(o1.timestamp - o2.timestamp).toInt()
        }
    })

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var carItem: CarData

        fun bind(car: CarData){
            carItem = car
            itemView.tvName.text = carItem.name
            itemView.tvYears.text = carItem.yearIssue.toString()
            itemView.tvClassification.text = carItem.classification

            itemView.imCar.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("citiName", carItem.name)
                itemView.findNavController().navigate(R.id.action_fullListCars_to_fullScreenCarImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return NewAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        sortedList.let { holder.bind(it.get(position)) }
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    fun getParamFilter(param: Boolean){
        flagFilter = param
    }

    fun getParamSort(sort: String){
        paramSort = sort
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(cars: List<CarData>,) {
        sortedList.replaceAll(cars)
    }
}
