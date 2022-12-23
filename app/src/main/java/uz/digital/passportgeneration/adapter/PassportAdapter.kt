package uz.digital.passportgeneration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.digital.passportgeneration.databinding.ItemLayoutBinding
import uz.digital.passportgeneration.model.Passport

class PassportAdapter : RecyclerView.Adapter<PassportAdapter.PassportViewHolder>() {

    lateinit var onClick: (Passport) -> Unit
    lateinit var onEdit: (Passport) -> Unit
    var passportList = mutableListOf<Passport>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassportViewHolder {
        return PassportViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return passportList.size
    }

    override fun onBindViewHolder(holder: PassportViewHolder, position: Int) {
        holder.bind(passport = passportList[position])
    }

    inner class PassportViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(passport: Passport) {
            binding.apply {
                name.text = "${adapterPosition.plus(1)}.${passport.lastName}${passport.name}"
                fatName.text = passport.fatName

                itemView.setOnClickListener {
                    onClick(passport)
                }
                edit.setOnClickListener {
                    onEdit(passport)
                }
            }
        }
    }

    fun filter(newList: MutableList<Passport>) {
        passportList = newList
        notifyDataSetChanged()
    }
}