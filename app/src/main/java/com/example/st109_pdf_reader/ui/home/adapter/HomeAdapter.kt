package com.example.st109_pdf_reader.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.st109_pdf_reader.ui.home.fragment.BookmarkFragment
import com.example.st109_pdf_reader.ui.home.fragment.HomeFragment
import com.example.st109_pdf_reader.ui.home.fragment.RecentFragment
import com.example.st109_pdf_reader.ui.home.fragment.SavedFragment


class HomeAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> RecentFragment()
            2 -> BookmarkFragment()
            else -> SavedFragment()
        }
    }
}