package com.example.initialdataentry.fragment.content

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.initialdataentry.Constants
import com.example.initialdataentry.Constants.Companion.CAR_NUM_CONTAIN
import com.example.initialdataentry.Constants.Companion.STS_CONTAIN
import com.example.initialdataentry.R
import com.example.initialdataentry.classes.ConvertAngToRu
import com.example.initialdataentry.classes.SharedPreference
import com.example.initialdataentry.databinding.FragmentInputStsBinding
import com.example.initialdataentry.interfaces.MainCallback


class InputStsFragment : Fragment() {
    private lateinit var sharedPreference: SharedPreference
    private lateinit var binding: FragmentInputStsBinding
    private var mainCallback: MainCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputStsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stsET.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                if (text.isEmpty()) {
                    mainCallback?.containsEditText(false)
                } else {
                    mainCallback?.containsEditText(true)
                }
            }
        })

        binding.stsET.setOnClickListener{
            binding.stsET.setBackgroundResource(R.drawable.default_background)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = InputStsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sharedPreference = SharedPreference(context)
        if (context is MainCallback) {
            mainCallback = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        mainCallback = null
    }

    fun correctEntry(): Boolean {
        val currentText = binding.stsET.text.toString()
        binding.stsET.setText(ConvertAngToRu().convert(currentText))

        var carNumMask = "^(\\d{2}[АВЕЗКМНОРСТУХ]{2}\\d{6}|\\d{10})$".toRegex()
        if (binding.stsET.text.toString().matches(carNumMask)) {

            mainCallback?.carNumberTransfer()
                ?.let { sharedPreference.sharedSave(CAR_NUM_CONTAIN, it) }

            sharedPreference.sharedSave(STS_CONTAIN, binding.stsET.text.toString())

            return true
        }

        binding.stsET.setBackgroundResource(R.drawable.red_edittext_background)
        binding.stsET?.let { editText ->
            editText.postDelayed({
                editText.requestFocus()
                editText.setSelection(editText.length())
            }, 16) // Задержка в миллисекундах
        }

        return false
    }
}