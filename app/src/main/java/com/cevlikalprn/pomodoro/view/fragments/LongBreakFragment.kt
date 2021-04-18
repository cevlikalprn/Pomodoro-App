package com.cevlikalprn.pomodoro.view.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.cevlikalprn.pomodoro.EditTimer
import com.cevlikalprn.pomodoro.LocalDataManager
import com.cevlikalprn.pomodoro.R
import com.cevlikalprn.pomodoro.databinding.FragmentLongBreakBinding


class LongBreakFragment : Fragment() {


    private lateinit var binding: FragmentLongBreakBinding

    private var checkBtnStart = true
    private var timerDuration: Long = 15*60000
    private var tick: Long = 1000
    private val defaultLongBreak = "15"
    private lateinit var longBreak: String

    private lateinit var longBreakTimer: CountDownTimer
    private lateinit var editTimer: EditTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_long_break, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTimer = EditTimer()

        val preferences = LocalDataManager.getPreferences(this.requireContext())

        //read data
        longBreak = preferences.getString("long_break",defaultLongBreak)!!

        timerDuration = longBreak.toLong() * 60000
        val stringTimer = editTimer.setTheTimer(timerDuration)
        binding.longBreakTxtview.text = stringTimer

        binding.btnStartLongBreak.setOnClickListener {
            startLongBreak()
        }
    }

    private fun startLongBreak() {
        if(checkBtnStart){
            binding.btnStartLongBreak.text = "Stop"
            checkBtnStart = false

            longBreakTimer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    findNavController().navigate(R.id.action_longBreakFragment_to_timerFragment)
                }
                override fun onTick(millisUntilFinished: Long) {
                    timerDuration = millisUntilFinished
                    val stringTimer = editTimer.setTheTimer(timerDuration)
                    binding.longBreakTxtview.text = stringTimer
                }
            }.start()
        }
        else{
            longBreakTimer.cancel()
            binding.btnStartLongBreak.text = "Start"
            checkBtnStart = true
        }
    }

}