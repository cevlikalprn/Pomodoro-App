package com.cevlikalprn.pomodoro

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
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


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
    private lateinit var shortBreak: String
    private lateinit var longBreak: String
    private lateinit var preferences: SharedPreferences

    //default values
    private val defaultPomodoro = "25"
    private val defaultShortBreak = "5"
    private val defaultLongBreak = "15"

    //Initialize
    private fun init(){
        btnShortBreak = requireView().findViewById(R.id.btn_short_break)
        btnLongBreak = requireView().findViewById(R.id.btn_long_break)
        btnStart = requireView().findViewById(R.id.btn_start_short_break)
        btnSettings = requireView().findViewById(R.id.btn_settings)
        txtTimer = requireView().findViewById(R.id.short_break_txtview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        preferences = requireContext().getSharedPreferences(requireContext().packageName,Context.MODE_PRIVATE)

        //read data
        pomodoro = preferences.getString("pomodoro", defaultPomodoro)!!
        shortBreak = preferences.getString("short_break", defaultShortBreak)!!
        longBreak = preferences.getString("long_break",defaultLongBreak)!!

        arguments?.let {
            val isItSet = TimerFragmentArgs.fromBundle(it).isItSet
            if(isItSet == true){    // SettingsFragment'da süre ayarlaması yapıldı mı?
                pomodoro = TimerFragmentArgs.fromBundle(it).pomodoro
                shortBreak = TimerFragmentArgs.fromBundle(it).shortBreak
                longBreak = TimerFragmentArgs.fromBundle(it).longBreak
            }
        }

        //put data
        preferences.edit().putString("pomodoro", pomodoro).apply()
        preferences.edit().putString("short_break", shortBreak).apply()
        preferences.edit().putString("long_break", longBreak).apply()


        timerDuration = pomodoro.toLong() * 60000
        setTheTimer(timerDuration)

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

    private fun start() {

        if(checkBtnStart){
            btnStart.text = "Stop"
            checkBtnStart = false

            timer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    btnStart.text = "Start"
                    counter++
                    if(counter %4 == 0){
                        findNavController().navigate(R.id.action_timerFragment_to_longBreakFragment)
                    }else{
                        findNavController().navigate(R.id.action_timerFragment_to_shortBreakFragment)
                    }
                }
                override fun onTick(millisUntilFinished: Long) {
                    timerDuration = millisUntilFinished
                    setTheTimer(millisUntilFinished)
                }
            }.start()
        }
        else{
            timer.cancel()
            btnStart.text = "Start"
            checkBtnStart = true
        }

    }

    private fun setTheTimer(millisUntilFinished: Long){
        val time = millisUntilFinished / 1000
        val minutes = time.toInt() / 60       //minutes
        val seconds = time.toInt() - minutes*60
        editTimerText(minutes ,seconds)
    }

    private fun editTimerText(minutes: Int ,seconds: Int){
        var stringMinutes = minutes.toString()
        var stringSeconds = seconds.toString()
        if(minutes <=9){
            stringMinutes = "0" + stringMinutes
        }
        if(seconds <= 9){
            stringSeconds = "0" + stringSeconds
        }
        txtTimer.text = "$stringMinutes:$stringSeconds"
    }

    private fun jumpToSettings(view: View) {
        val action = TimerFragmentDirections.actionTimerFragmentToSettingsFragment()
        view.findNavController().navigate(action)
    }

    private fun jumpToLongBreak(view: View) {
        val action = TimerFragmentDirections.actionTimerFragmentToLongBreakFragment(longBreak)
        view.findNavController().navigate(action)
    }

    private fun jumpToShortBreak(view: View) {
        val action = TimerFragmentDirections.actionTimerFragmentToShortBreakFragment(shortBreak)
        view.findNavController().navigate(action)
    }

}