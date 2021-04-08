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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cevlikalprn.pomodoro.EditTimer
import com.cevlikalprn.pomodoro.LocalDataManager
import com.cevlikalprn.pomodoro.R
import com.cevlikalprn.pomodoro.databinding.FragmentTimerBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding

    //Değişkenler
    private var timerDuration: Long = 25*60000 // ayarlanan süre
    private var tick: Long = 1000               // tetiklenen süre
    private var checkBtnStart = true            // start fonksiyonunda gerekli durumlara girmek için boolean değer kontrolü
    private lateinit var timer: CountDownTimer  //zamanlayıcı
    private var counter = 0 // mola dilimini ayarlamak için gerekli sayaç. 4'ün katlarında longBreak diğerlerinde shortBreak.
    private lateinit var pomodoro: String
    //default values
    private val defaultPomodoro = "25"
    //Edit Timer
    private lateinit var editTimer: EditTimer // Timer'ı düzenleyen class


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_timer,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTimer = EditTimer()

        //read data
        val preferences = LocalDataManager.getPreferences(this.requireContext())
        pomodoro = preferences.getString("pomodoro", defaultPomodoro)!!

        timerDuration = pomodoro.toLong() * 60000
        val stringTimer = editTimer.setTheTimer(timerDuration)
        binding.txtTimer.text = stringTimer


        //Buttons
        binding.btnShortBreak.setOnClickListener {
            jumpToShortBreak(it)
        }
        binding.btnLongBreak.setOnClickListener {
            jumpToLongBreak(it)
        }
        binding.btnSettings.setOnClickListener {
            jumpToSettings(it)
        }
        binding.btnStartShortBreak.setOnClickListener {
            start()
        }

    }

    //Start the timer
    private fun start() {

        if(checkBtnStart){
            binding.btnStartShortBreak.text = "Stop"
            checkBtnStart = false

            timer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    binding.btnStartShortBreak.text = "Start"
                    counter++
                    if(counter %4 == 0){ // Go to LongBreak

                        //findNavController().navigate(R.id.action_timerFragment_to_longBreakFragment)
                        val action = TimerFragmentDirections.actionTimerFragmentToLongBreakFragment()
                        findNavController().navigate(action)

                    }else{ //Go to ShortBreak

                        //findNavController().navigate(R.id.action_timerFragment_to_shortBreakFragment)
                        val action = TimerFragmentDirections.actionTimerFragmentToShortBreakFragment()
                        findNavController().navigate(action)

                    }
                }
                override fun onTick(millisUntilFinished: Long) {
                    timerDuration = millisUntilFinished
                    val stringTimer = editTimer.setTheTimer(millisUntilFinished)
                    binding.txtTimer.text = stringTimer
                }
            }.start()
        }
        else{
            timer.cancel()
            binding.btnStartShortBreak.text = "Start"
            checkBtnStart = true
        }

    }

    // Jump to somewhere
    private fun jumpToSettings(view: View) {
        val action = TimerFragmentDirections.actionTimerFragmentToSettingsFragment()
        view.findNavController().navigate(action)
    }

    private fun jumpToLongBreak(view: View) {
        val action = TimerFragmentDirections.actionTimerFragmentToLongBreakFragment()
        view.findNavController().navigate(action)
    }

    private fun jumpToShortBreak(view: View) {
        val action = TimerFragmentDirections.actionTimerFragmentToShortBreakFragment()
        view.findNavController().navigate(action)
    }

}