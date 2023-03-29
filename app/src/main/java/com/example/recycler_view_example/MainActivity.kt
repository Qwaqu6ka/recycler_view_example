package com.example.recycler_view_example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recycler_view_example.databinding.ActivityMainBinding
import com.example.recycler_view_example.screens.UserDetailsFragment
import com.example.recycler_view_example.screens.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainer.id, UsersListFragment())
                .commit()
        }
    }

    override fun onUserDetails(userId: Long) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, UserDetailsFragment.getInstance(userId))
            .addToBackStack(null)
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}