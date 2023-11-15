package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.UserInfoBinding

//5 saatia da validaciis dawera agar minda iyos -10 qula

class MainActivity : AppCompatActivity(), UserItemClickHandler {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: MutableList<User>
    private lateinit var adapter: UsersAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and set its layout manager
        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        // Initialize and set adapter for RecyclerView
        userList = mutableListOf()
        adapter = UsersAdapter(userList, this)
        binding.rvUsers.adapter = adapter

        // Set up click listener for the "Add User" button
        binding.btnAddUser.setOnClickListener {
            showUserDialog(null) // Passing null for new user
        }

    }

    override fun onDeleteClick(user: User) {
        val position = userList.indexOf(user)
        userList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onChangeClick(user: User) {
        showUserDialog(user)
    }



    private fun showUserDialog(user: User?) {
        val dialogBinding = UserInfoBinding.inflate(layoutInflater)

        // Pre-fill the EditTexts with the user information if it's an edit operation
        if (user != null) {
            dialogBinding.editName.setText(user.name)
            dialogBinding.editSurname.setText(user.surname)
            dialogBinding.editEmail.setText(user.email)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle(if (user == null) "Add User" else "Change User")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                val name = dialogBinding.editName.text.toString()
                val surname = dialogBinding.editSurname.text.toString()
                val email = dialogBinding.editEmail.text.toString()

                if (user == null) {
                    // Add a new user to the dataset
                    val newUser = User(id = userList.size + 1, name = name, surname = surname, email = email)
                    userList.add(newUser)
                    adapter.notifyItemInserted(userList.size - 1)
                } else {
                    // Update the user in the list with the new information
                    val updatedUser = User(id = user.id, name = name, surname = surname, email = email)
                    val position = userList.indexOf(user)
                    userList[position] = updatedUser
                    adapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
