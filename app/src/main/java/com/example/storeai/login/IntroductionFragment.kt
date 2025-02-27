package com.example.storeai.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storeai.R
import com.example.storeai.databinding.FragmentIntroductionBinding

class IntroductionFragment : Fragment(R.layout.fragment_introduction) {
    private lateinit var binding: FragmentIntroductionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIntroductionBinding.bind(view)

        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_introduction_to_accountOptionsFragment)
        }
    }
}