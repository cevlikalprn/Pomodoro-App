package com.cevlikalprn.pomodoro.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.replace
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.cevlikalprn.pomodoro.R
import com.cevlikalprn.pomodoro.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var databinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

}
