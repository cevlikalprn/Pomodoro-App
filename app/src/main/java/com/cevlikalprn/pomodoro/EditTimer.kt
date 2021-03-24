package com.cevlikalprn.pomodoro

class EditTimer {

    fun setTheTimer(millisUntilFinished: Long): String{
        val time = millisUntilFinished / 1000
        val minutes = time.toInt() / 60
        val seconds = time.toInt() - minutes*60

        var stringMinutes = minutes.toString()
        var stringSeconds = seconds.toString()
        if(minutes <=9){
            stringMinutes = "0" + stringMinutes
        }
        if(seconds <= 9){
            stringSeconds = "0" + stringSeconds
        }
        val stringTimer = stringMinutes+ ":" + stringSeconds
        return stringTimer
    }


}