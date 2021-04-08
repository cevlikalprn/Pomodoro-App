package com.cevlikalprn.pomodoro.view

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.cevlikalprn.pomodoro.EditTimer
import com.cevlikalprn.pomodoro.LocalDataManager
import com.cevlikalprn.pomodoro.R
import com.cevlikalprn.pomodoro.databinding.FragmentShortBreakBinding


class ShortBreakFragment : Fragment() {

    private lateinit var binding: FragmentShortBreakBinding

    private var checkBtnStart = true
    private var timerDuration: Long = 5*60000
    private var tick: Long = 1000
    private val defaultShortBreak = "5"
    private lateinit var shortBreak: String

    private lateinit var shortBreakTimer: CountDownTimer

    private lateinit var editTimer: EditTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_short_break, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTimer = EditTimer()

        val preferences = LocalDataManager.getPreferences(this.requireContext())

        //read data
        shortBreak = preferences.getString("short_break", defaultShortBreak)!!


        timerDuration = shortBreak.toLong() * 60000
        val stringTimer = editTimer.setTheTimer(timerDuration)
        binding.shortBreakTxtview.text = stringTimer

        binding.btnStartShortBreak.setOnClickListener {
            startShortBreak()
        }
    }

    private fun startShortBreak() {

        if(checkBtnStart){
            binding.btnStartShortBreak.text = "Stop"
            checkBtnStart = false

            shortBreakTimer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    findNavController().navigate(R.id.action_shortBreakFragment_to_timerFragment)
                }
                override fun onTick(millisUntilFinished: Long) {
                    timerDuration = millisUntilFinished
                    val stringTimer = editTimer.setTheTimer(timerDuration)
                    binding.shortBreakTxtview.text = stringTimer
                }
            }.start()
        }
        else{
            shortBreakTimer.cancel()
            binding.btnStartShortBreak.text = "Start"
            checkBtnStart = true
        }
    }
}