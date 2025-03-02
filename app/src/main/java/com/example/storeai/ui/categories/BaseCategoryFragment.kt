package com.example.storeai.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storeai.R

class BaseCategoryFragment : Fragment() {
    private var categoryId: String? = null

    companion object {
        fun newInstance(categoryId: String) = BaseCategoryFragment().apply {
            arguments = Bundle().apply {
                putString("categoryId", categoryId)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        categoryId = arguments?.getString("categoryId")
        return inflater.inflate(R.layout.fragment_base_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Load products for this category using categoryId
    }
}