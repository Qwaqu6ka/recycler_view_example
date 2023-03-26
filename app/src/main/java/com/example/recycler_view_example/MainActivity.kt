package com.example.recycler_view_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_example.databinding.ActivityMainBinding
import com.example.recycler_view_example.models.User
import com.example.recycler_view_example.models.UserListener
import com.example.recycler_view_example.models.UserService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private val userService: UserService
        get() = (application as App).userService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onUserRemove(user: User) {
                userService.deleteUser(user)
            }

            override fun onUserMove(user: User, moveBy: Int) {
                userService.moveUser(user, moveBy)
            }

        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        userService.addListener(usersListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        userService.removeListener(usersListener)
    }

    private val usersListener: UserListener = {
        adapter.users = it
    }
}