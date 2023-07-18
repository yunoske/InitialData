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
import androidx.core.content.ContextCompat
import com.example.initialdataentry.Constants.Companion.CAR_NUM_CONTAIN
import com.example.initialdataentry.R
import com.example.initialdataentry.classes.ConvertAngToRu
import com.example.initialdataentry.classes.SharedPreference
import com.example.initialdataentry.databinding.FragmentInputGrzBinding
import com.example.initialdataentry.interfaces.MainCallback
import java.lang.StringBuilder
import kotlin.time.Duration.Companion.convert


class InputGrzFragment : Fragment() {
    private lateinit var binding: FragmentInputGrzBinding
    private var mainCallback: MainCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputGrzBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = InputGrzFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainCallback) {
            mainCallback = context
        }

    }

    override fun onDetach() {
        super.onDetach()

        mainCallback = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.grzET.addTextChangedListener(object : TextWatcher {
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

        binding.grzET.setOnClickListener{
            binding.grzET.setBackgroundResource(R.drawable.default_background)
        }
    }

    //Проверка коректности номера в EditText
    fun correctEntry(): Boolean {

        val currentText = binding.grzET.text.toString()
        binding.grzET.setText(ConvertAngToRu().convert(currentText))

        var carNumMask = "^[АВСЕНКМОРТХУ]{1}\\d{3}[АВСЕНКМОРТХУ]{2}\\d{2}|[ТКС]?[АВЕКМНОРСТУХ]{2}\\d{3}\\d{2}|[АВЕКМНОРСТУХ]{2}\\d{4}\\d{2}|" +
                "\\d{4}[АВЕКМНОРСТУХ]{2}\\d{2}|[АВЕКМНОРСТУХ]{2}\\d{2}[АВЕКМНОРСТУХ]{2}\\d{2}|\\d{3}[С]{1}[D]{1}\\d\\d{2}|\\d{3}[DТ]{1}\\d{3}\\d{2}|" +
                "[D]{1}\\d{5}\\d{2}|[АВЕКМНОРСТУХ]{1}\\d{4}\\d{2}|\\d{3}[АВЕКМНОРСТУХ]{1}\\d{2}|\\d{4}[АВЕКМНОРСТУХ]{1}\\d{2}|" +
                "[КС]?\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2}|\\d{4}[АВЕКМНОРСТУХ]{1}\\d{2}|\\d{4}[АВЕКМНОРСТУХ]{1}\\d{2}$"
        if (binding.grzET.text.toString().matches(carNumMask.toRegex())) {
            mainCallback?.carNumberTransfer(binding.grzET.text.toString())
            return true
        }

        binding.grzET.setBackgroundResource(R.drawable.red_edittext_background)
        binding.grzET?.let { editText ->
            editText.postDelayed({
                editText.requestFocus()
                editText.setSelection(editText.length())
                                 }, 16) // Задержка в миллисекундах
            }
        return false
    }
}
