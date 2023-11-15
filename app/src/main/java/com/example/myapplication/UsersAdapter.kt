package com.example.myapplication
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.UserItemBinding


interface UserItemClickHandler {
    fun onDeleteClick(user: User)
    fun onChangeClick(user: User)
}

class UsersAdapter(private val userList: List<User>, private val userItemClickHandler: UserItemClickHandler) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Set the click listener for the buttons in the constructor
            binding.btnDelete.setOnClickListener { userItemClickHandler.onDeleteClick(userList[adapterPosition]) }
            binding.btnChange.setOnClickListener { userItemClickHandler.onChangeClick(userList[adapterPosition]) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.user = currentUser
        holder.binding.executePendingBindings()
    }

}


