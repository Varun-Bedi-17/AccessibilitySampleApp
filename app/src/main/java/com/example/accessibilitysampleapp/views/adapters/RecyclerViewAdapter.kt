package com.example.accessibilitysampleapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accessibilitysampleapp.databinding.RvListItemBinding
import com.example.accessibilitysampleapp.models.response.TitleWithContentResponseItem

class RecyclerViewAdapter(
    private val itemsList: List<TitleWithContentResponseItem>,
    private val onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewListHolder>() {

    private lateinit var binding: RvListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewListHolder {

        // Binding
        binding = RvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewListHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerViewListHolder, position: Int) {
        binding.title.text = itemsList[position].title
        binding.clTitle.setOnClickListener {
            onItemClickListener.itemClickCallback(position)
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
    // Items not repeat
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    class RecyclerViewListHolder(binding: RvListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener{
        fun itemClickCallback(position : Int)
    }
}