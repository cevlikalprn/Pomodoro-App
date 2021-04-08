package com.cevlikalprn.pomodoro.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.cevlikalprn.pomodoro.LocalDataManager
import com.cevlikalprn.pomodoro.R
import com.cevlikalprn.pomodoro.databinding.FragmentSettingsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsFragment : Fragment() {

   private lateinit var binding: FragmentSettingsBinding

    private var pomodoro = "25" //default pomodoro süresi
    private var shortBreak = "5" //default short break süresi
    private var longBreak = "15" //default long break süresi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOk.setOnClickListener {
            backToTimer(it)
        }

    }

    private fun backToTimer(view: View) {
        val shallWeGo = getData()
        if(shallWeGo){
            val action = SettingsFragmentDirections.actionSettingsFragmentToTimerFragment()
            view.findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(), "Please set the timer", Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(): Boolean{
        if(binding.edtPomodoroTime.text.toString() != ""
            && binding.edtShortBreakTime.text.toString() != ""
            && binding.edtLongBreakTime.text.toString() != ""
        ){

            pomodoro = binding.edtPomodoroTime.text.toString()
            shortBreak = binding.edtShortBreakTime.text.toString()
            longBreak = binding.edtLongBreakTime.text.toString()

            //Put local data
            val preferences = LocalDataManager.getPreferences(this.requireContext())
            preferences.edit().putString("pomodoro", pomodoro).apply()
            preferences.edit().putString("short_break", shortBreak).apply()
            preferences.edit().putString("long_break", longBreak).apply()

            return true
        }
        return false
    }
}