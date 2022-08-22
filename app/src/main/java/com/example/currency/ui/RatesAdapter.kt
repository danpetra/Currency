package com.example.currency.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.entities.FinalEntity
import com.example.currency.databinding.RateItemBinding

class RatesAdapter: RecyclerView.Adapter<RatesAdapter.ViewHolder>() {

    private var keysList = listOf("")
    private var valuesList = listOf(0f)
    private var isMarkedList = listOf(false)

    var clickListener = { _:Int, _:String -> }

    fun setList(list: List<FinalEntity>){
        keysList = list.map { it.key }
        valuesList = list.map { it.rate }
        isMarkedList = list.map { it.isMarked }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RateItemBinding.inflate(LayoutInflater.from(parent.context)), clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            keysList[position],
            valuesList[position],
            isMarkedList[position]
        )
    }

    override fun getItemCount() = keysList.size

    class ViewHolder(private val binding: RateItemBinding, var clickListener: (Int, String)->Unit)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, rate: Float, isMarked: Boolean){
            binding.name.text = name
            binding.rate.text = rate.toString()
            binding.addButton.setImageResource(isMarked.toImage())
            binding.addButton.setOnClickListener {
                clickListener(adapterPosition, name)
            }
        }

        private fun Boolean.toImage(): Int{
            return if (this) R.drawable.ic_star_fillin_24
            else R.drawable.ic_star_border_24
        }
    }


}