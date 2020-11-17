package com.example.baatcheet.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baatcheet.ui.ChatsFragment
import com.example.baatcheet.ui.PeopleFragment

class FragmentsAdapter(activity:AppCompatActivity):FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment:Fragment
        when(position){
           0->{
               fragment= ChatsFragment()
           }
            else->{
               fragment= PeopleFragment()
            }
        }
        return fragment
    }

}