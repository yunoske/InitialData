package com.example.initialdataentry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.initialdataentry.Constants.Companion.CAR_NUM_CONTAIN
import com.example.initialdataentry.Constants.Companion.STS_CONTAIN
import com.example.initialdataentry.Constants.Companion.VU_CONTAIN
import com.example.initialdataentry.classes.SharedPreference
import com.example.initialdataentry.fragment.content.InputGrzFragment
import com.example.initialdataentry.fragment.content.InputStsFragment
import com.example.initialdataentry.fragment.content.InputVuFragment
import com.example.initialdataentry.fragment.content.ResultFragment
import com.example.initialdataentry.fragment.control.ControlPanelFragment
import com.example.initialdataentry.interfaces.MainCallback

class MainActivity : AppCompatActivity(), MainCallback {
    private lateinit var sharedPreference: SharedPreference
    private var currentFragmentIndex = 0
    private var carNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = SharedPreference(this)


        val transaction = supportFragmentManager.beginTransaction()
        if (!sharedPreference.sharedContains(CAR_NUM_CONTAIN)) {
            transaction.replace(R.id.contentLayout, InputGrzFragment.newInstance())
            transaction.replace(R.id.controlPanelLayout, ControlPanelFragment.newInstance(), "controlPanaleFragmentTag")
            currentFragmentIndex = 1
        } else if (!sharedPreference.sharedContains(VU_CONTAIN)) {
            transaction.replace(R.id.contentLayout, InputVuFragment.newInstance())
                .replace(R.id.controlPanelLayout, ControlPanelFragment.newInstance(), "controlPanaleFragmentTag")
            currentFragmentIndex = 3
        } else {
            transaction.replace(R.id.contentLayout, ResultFragment.newInstance())
            currentFragmentIndex = 4
        }
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (!sharedPreference.sharedContains(STS_CONTAIN)) sharedPreference.sharedRemove(CAR_NUM_CONTAIN)
    }

    override fun carNumButtonClicked(boolean: Boolean): Boolean {
        var correct = false

        val transaction = supportFragmentManager.beginTransaction()

        if (boolean) {
            when (currentFragmentIndex) {
                1 -> {
                    correct = (supportFragmentManager.findFragmentById(R.id.contentLayout) as? InputGrzFragment)?.correctEntry() ?: false
                    if (correct) {
                        transaction.replace(R.id.contentLayout, InputStsFragment()).commit()
                        ++currentFragmentIndex
                    }
                }
                2 -> {
                    correct = (supportFragmentManager.findFragmentById(R.id.contentLayout) as? InputStsFragment)?.correctEntry() ?: false
                    if (correct) {
                        if (sharedPreference.sharedContains(VU_CONTAIN)) {
                            val fragmentToRemove = supportFragmentManager.findFragmentByTag("controlPanaleFragmentTag")
                            if (fragmentToRemove != null) {
                                transaction.remove(fragmentToRemove)
                            }
                            transaction.replace(R.id.contentLayout, ResultFragment()).commit()
                        } else {
                            transaction.replace(R.id.contentLayout, InputVuFragment()).commit()
                            ++currentFragmentIndex
                        }
                    }
                }
                3 -> {
                    correct = (supportFragmentManager.findFragmentById(R.id.contentLayout) as? InputVuFragment)?.correctEntry() ?: false
                    if (correct) {
                        val fragmentToRemove = supportFragmentManager.findFragmentByTag("controlPanaleFragmentTag")
                        if (fragmentToRemove != null) {
                            transaction.remove(fragmentToRemove)
                        }
                        transaction.replace(R.id.contentLayout, ResultFragment()).commit()
                        ++currentFragmentIndex
                    }
                }
            }
            return correct
        }
            //запрос на пропуск
        when (currentFragmentIndex) {
            1 -> {
                transaction.replace(R.id.contentLayout, InputVuFragment()).commit()
                currentFragmentIndex = 3
            }
            2 -> {
                transaction.replace(R.id.contentLayout, InputVuFragment()).commit()
                sharedPreference.sharedRemove(CAR_NUM_CONTAIN)
                currentFragmentIndex++
            }
            3 -> {
                val fragmentToRemove = supportFragmentManager.findFragmentByTag("controlPanaleFragmentTag")
                if (fragmentToRemove != null) {
                    transaction.remove(fragmentToRemove)
                }
                transaction.replace(R.id.contentLayout, ResultFragment()).commit()
            }
        }
        return false
    }

    override fun containsEditText(boolean: Boolean){
        (supportFragmentManager.findFragmentById(R.id.controlPanelLayout) as? ControlPanelFragment)?.butSckipOrNext(boolean)
    }

    override fun carNumberTransfer(number: String) {
        carNumber = number
    }

    override fun carNumberTransfer(): String {
        return carNumber
    }
}