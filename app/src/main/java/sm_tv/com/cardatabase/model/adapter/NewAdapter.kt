package sm_tv.com.cardatabase.model.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.car_item.view.*
import sm_tv.com.cardatabase.R
import sm_tv.com.cardatabase.model.CarData


class NewAdapter : RecyclerView.Adapter<NewAdapter.MyViewHolder>() {
    //private var sortedList = SortedList<Note>()
    private var paramSort = ""
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
            if (flagFilter && o2.yearIssue > o1.yearIssue) {
                return 1
            }
            if (o2.name == paramSort && o1.name != paramSort) {
                return 1
            }
            return if (o2.name != paramSort && o1.name == paramSort) {
                -1
            } else (o1.timestamp - o2.timestamp).toInt()
        }
    })

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var carItem: CarData

        fun bind(car: CarData) {
            carItem = car
            itemView.tvName.text = carItem.name
            itemView.tvYears.text = carItem.yearIssue.toString()
            itemView.tvClassification.text = carItem.classification
            val uri = Uri.parse(carItem.url)
            Picasso.get()
                .load(uri)
                .resize(200, 200)
                .placeholder(R.drawable.ic_baseline_directions_car_24)
                .error(R.drawable.ic_baseline_delete_24)
                .into(itemView.imCar, object : Callback {
                    override fun onSuccess() {
                        println("__________________ONSUCCESS")
                    }

                    override fun onError(e: Exception?) {
                        println("____________________$e")
                    }
                })
            itemView.imCar.setOnClickListener {
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

    fun getParamFilter(param: Boolean) {
        flagFilter = param
    }

    fun getParamSort(sort: String) {
        paramSort = sort
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(cars: List<CarData>) {
        sortedList.replaceAll(cars)
    }
}
