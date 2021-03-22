package com.cevlikalprn.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class LongBreakFragment : Fragment() {

    private lateinit var btnStart: Button
    private lateinit var btnBack: ImageButton
    private lateinit var longBreakTxt: TextView

    private var checkBtnStart = true
    private var timerDuration: Long = 15*60000
    private var tick: Long = 1000
    private lateinit var longBreak: String

    private lateinit var longBreakTimer: CountDownTimer

    private fun init(){
        btnStart = requireView().findViewById(R.id.btn_start_long_break)
        btnBack = requireView().findViewById(R.id.btn_long_break_to_timer)
        longBreakTxt = requireView().findViewById(R.id.long_break_txtview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_long_break, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        arguments?.let {
            longBreak = TimerFragmentArgs.fromBundle(it).longBreak
        }
        timerDuration = longBreak.toLong() * 60000
        setTheLongBreakTimer(timerDuration)

        btnStart.setOnClickListener {
            startLongBreak()
        }
        btnBack.setOnClickListener {
            backToTimer(it)
        }
    }

    private fun backToTimer(view: View) {
        val action = LongBreakFragmentDirections.actionLongBreakFragmentToTimerFragment()
        view.findNavController().navigate(action)
    }

    private fun startLongBreak() {
        if(checkBtnStart){
            btnStart.text = "Stop"
            checkBtnStart = false

            longBreakTimer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    findNavController().navigate(R.id.action_longBreakFragment_to_timerFragment)
                }
                override fun onTick(millisUntilFinished: Long) {
                    timerDuration = millisUntilFinished
                    setTheLongBreakTimer(millisUntilFinished)
                }
            }.start()
        }
        else{
            longBreakTimer.cancel()
            btnStart.text = "Start"
            checkBtnStart = true
        }
    }

    private fun setTheLongBreakTimer(millisUntilFinished: Long){
        val time = millisUntilFinished / 1000
        val minutes = time.toInt() / 60
        val seconds = time.toInt() - minutes*60
        longBreakTxt.text =  "$minutes:$seconds"
    }

}