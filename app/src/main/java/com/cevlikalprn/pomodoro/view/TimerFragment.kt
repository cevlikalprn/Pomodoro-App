package com.cevlikalprn.pomodoro.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cevlikalprn.pomodoro.EditTimer
import com.cevlikalprn.pomodoro.LocalDataManager
import com.cevlikalprn.pomodoro.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TimerFragment : Fragment() {
    //Views
    private lateinit var btnShortBreak : Button
    private lateinit var btnLongBreak: Button
    private lateinit var btnStart: Button
    private lateinit var btnSettings: FloatingActionButton
    private lateinit var txtTimer: TextView
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
    private lateinit var editTimer: EditTimer

    private fun init(){
        btnShortBreak = requireView().findViewById(R.id.btn_short_break)
        btnLongBreak = requireView().findViewById(R.id.btn_long_break)
        btnStart = requireView().findViewById(R.id.btn_start_short_break)
        btnSettings = requireView().findViewById(R.id.btn_settings)
        txtTimer = requireView().findViewById(R.id.short_break_txtview)

        editTimer = EditTimer() // Timer'ı düzenleyen class
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        //read data
        val preferences = LocalDataManager.getPreferences(this.requireContext())
        pomodoro = preferences.getString("pomodoro", defaultPomodoro)!!

        timerDuration = pomodoro.toLong() * 60000
        val stringTimer = editTimer.setTheTimer(timerDuration)
        txtTimer.text = stringTimer


        //Buttons
        btnShortBreak.setOnClickListener {
            jumpToShortBreak(it)
        }
        btnLongBreak.setOnClickListener {
            jumpToLongBreak(it)
        }
        btnSettings.setOnClickListener {
            jumpToSettings(it)
        }
        btnStart.setOnClickListener {
            start()
        }

    }
    //Start the timer
    private fun start() {

        if(checkBtnStart){
            btnStart.text = "Stop"
            checkBtnStart = false

            timer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    btnStart.text = "Start"
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
                    txtTimer.text = stringTimer
                }
            }.start()
        }
        else{
            timer.cancel()
            btnStart.text = "Start"
            checkBtnStart = true
        }

    }

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