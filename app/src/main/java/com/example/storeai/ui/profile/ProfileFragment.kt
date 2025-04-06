package com.example.storeai.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storeai.activities.LoginRegisterActivity
import com.example.storeai.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
        setupLogoutButton()
    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val email = sharedPref.getString("email", "")
        val userId = sharedPref.getString("user_id", "")

        // Display user data
        binding.tvUsername.text = username
        binding.tvEmail.text = email
        binding.tvUserId.text = "User ID: $userId"

        // Set initial from username
        val initial = if (username.isNullOrEmpty()) "" else username.first().toString()
        binding.tvProfileInitial.text = initial
    }

    private fun setupLogoutButton() {
        binding.buttonLogout.setOnClickListener {
            // Clear SharedPreferences
            val sharedPref = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            // Navigate to LoginRegisterActivity and finish current activity
            startActivity(Intent(requireContext(), LoginRegisterActivity::class.java))
            requireActivity().finish()
        }
    }
}