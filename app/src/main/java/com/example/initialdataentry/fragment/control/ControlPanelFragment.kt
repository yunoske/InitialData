package com.example.initialdataentry.fragment.control

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.initialdataentry.databinding.FragmentControlPanelBinding
import com.example.initialdataentry.interfaces.MainCallback

class ControlPanelFragment : Fragment(){
    private lateinit var binding: FragmentControlPanelBinding

    private var mainCallback: MainCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentControlPanelBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ControlPanelFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener{
            if (binding.next.text == "Далее") {
                val et = mainCallback?.carNumButtonClicked(true)
                if (et == true) {
                    binding.next.text = "Пропустить"
                }
            } else {
                showConfirmationDialog()
            }

        }
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

    fun butSckipOrNext(boolean: Boolean) {
        if (boolean) {
            binding.next.text = "Далее"
        } else {
            binding.next.text = "Пропустить"
        }
    }

    private fun showConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(activity)

        dialogBuilder.setMessage("Вы действительно хотите пропустить?")
            .setCancelable(true)
            .setPositiveButton("Да") { dialog, id ->
                dialog.dismiss()
                // Здесь можно выполнить нужные действия при пропуске
                mainCallback?.carNumButtonClicked(false)
            }
            .setNegativeButton("Нет") { dialog, id ->
                // Обработка нажатия кнопки "Нет"
                dialog.dismiss()
                // Здесь можно выполнить другие действия или ничего не делать
            }
            .show()
    }
}