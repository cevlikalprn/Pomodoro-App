package com.cevlikalprn.pomodoro.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.cevlikalprn.pomodoro.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsFragment : Fragment() {

    private lateinit var btnOk: FloatingActionButton
    private lateinit var pomodoroTime: EditText
    private lateinit var shortBreakTime: EditText
    private lateinit var longBreakTime: EditText

    private var pomodoro = "25" //default pomodoro süresi
    private var shortBreak = "5" //default short break süresi
    private var longBreak = "15" //default long break süresi

    private fun init(){
        btnOk = requireView().findViewById(R.id.btn_ok)
        pomodoroTime = requireView().findViewById(R.id.edt_pomodoro_time)
        shortBreakTime = requireView().findViewById(R.id.edt_short_break_time)
        longBreakTime = requireView().findViewById(R.id.edt_long_break_time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        btnOk.setOnClickListener {
            backToTimer(it)
        }

    }

    private fun backToTimer(view: View) {
        val shallWeGo = getData()
        if(shallWeGo){
            val action = SettingsFragmentDirections.actionSettingsFragmentToTimerFragment(
                pomodoro,
                shortBreak,
                longBreak,
                true
            )
            view.findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(), "Please set the timer", Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(): Boolean{
        if(pomodoroTime.text.toString() != "" && shortBreakTime.text.toString() != "" && longBreakTime.text.toString() != ""){
            pomodoro = pomodoroTime.text.toString()
            shortBreak = shortBreakTime.text.toString()
            longBreak = longBreakTime.text.toString()
            return true
        }
        return false
    }
}