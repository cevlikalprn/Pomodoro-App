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
import kotlin.concurrent.timer


class ShortBreakFragment : Fragment() {

    private lateinit var btnStart: Button
    private lateinit var btnBack: ImageButton
    private lateinit var shortBreakTxt: TextView

    private var checkBtnStart = true
    private var timerDuration: Long = 5*60000
    private var tick: Long = 1000
    private lateinit var shortBreak: String

    private lateinit var shortBreakTimer: CountDownTimer

    private lateinit var editTimer: EditTimer

    private fun init(){
        btnStart = requireView().findViewById(R.id.btn_start_short_break)
        btnBack = requireView().findViewById(R.id.btn_short_break_to_timer)
        shortBreakTxt = requireView().findViewById(R.id.short_break_txtview)

        editTimer = EditTimer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_break, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        arguments?.let {
            shortBreak = TimerFragmentArgs.fromBundle(it).shortBreak
        }
        timerDuration = shortBreak.toLong() * 60000
        val stringTimer = editTimer.setTheTimer(timerDuration)
        shortBreakTxt.text = stringTimer

        btnStart.setOnClickListener {
            startShortBreak()
        }
        btnBack.setOnClickListener {
            backToTimer(it)
        }

    }

    private fun backToTimer(view: View) {
        val action = ShortBreakFragmentDirections.actionShortBreakFragmentToTimerFragment()
        view.findNavController().navigate(action)
    }

    private fun startShortBreak() {

        if(checkBtnStart){
            btnStart.text = "Stop"
            checkBtnStart = false

            shortBreakTimer = object: CountDownTimer(timerDuration, tick){
                override fun onFinish() {
                    findNavController().navigate(R.id.action_shortBreakFragment_to_timerFragment)
                }
                override fun onTick(millisUntilFinished: Long) {
                    timerDuration = millisUntilFinished
                    val stringTimer = editTimer.setTheTimer(timerDuration)
                    shortBreakTxt.text = stringTimer
                }
            }.start()
        }
        else{
            shortBreakTimer.cancel()
            btnStart.text = "Start"
            checkBtnStart = true
        }
    }
}