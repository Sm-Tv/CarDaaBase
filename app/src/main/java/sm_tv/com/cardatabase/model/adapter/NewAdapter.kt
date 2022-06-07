package sm_tv.com.cardatabase.model.adapter

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
    private lateinit var paramSort :String

    private var sortedList = SortedList(CarData::class.java, object : SortedList.Callback<CarData>() {

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
            if (o2.name == paramSort && o1.name != paramSort) {
                return 1
            }
            return  if (o2.name != paramSort && o1.name == paramSort) {
               -1
            }else(o1.timestamp - o2.timestamp).toInt()
        }
    })

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //private lateinit var  mViewModel: NoteViewModels
        private lateinit var carItem: CarData
        //private var siletn: Boolean = true

        fun bind(car: CarData){
            carItem = car
            itemView.tvName.text = carItem.name
            itemView.tvYears.text = carItem.yearIssue.toString()
            itemView.tvClassification.text = carItem.classification

            itemView.imCar.setOnClickListener {
                //val action = ListFragmentDirections.actionListFragmentToUpdateFeagment(note)
                val bundle = Bundle()
                bundle.putString("citiName", carItem.name)
                itemView.findNavController().navigate(R.id.action_fullListCars_to_fullScreenCarImage)
            }

            /*val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(recyclerView) {
                override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                    val deleteButton = deleteButton( itemView, context, viewModel )
                    val updateButton = updateButton(itemView, context)
                    return listOf(deleteButton, updateButton)
                }
            })
            //itemTouchHelper.attachToRecyclerView(recyclerView)*/
        }

        /*fun deleteButton(view: View, context: Context, viewModel: CarDataViewModel): SwipeHelper.UnderlayButton{
            return SwipeHelper.UnderlayButton(
                context,
                "Delete",
                14.0f,
                android.R.color.holo_red_light,
                object : SwipeHelper.UnderlayButtonClickListener {
                    override fun onClick() {
                        viewModel.deleteCarData(carItem)
                    }
                })
        }*/

       /* fun updateButton( view: View, context: Context): SwipeHelper.UnderlayButton{
            return SwipeHelper.UnderlayButton(
                context,
                "Update",
                14.0f,
                android.R.color.holo_blue_light,
                object : SwipeHelper.UnderlayButtonClickListener {
                    override fun onClick() {
                        val carData = carItem
                        val action = FullListCarsDirections.actionFullListCarsToUpdateCarFragment(carData)
                        view.findNavController().navigate(action)
                        Toast.makeText(context, "updateButton", Toast.LENGTH_SHORT).show()
                    }
                })
        }*/
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

    fun setItems(cars: List<CarData>, sort: String,) {
        paramSort = sort
        sortedList.replaceAll(cars)
    }
}
