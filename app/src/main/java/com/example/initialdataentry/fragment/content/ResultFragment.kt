package com.example.initialdataentry.fragment.content

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.initialdataentry.Constants.Companion.CAR_NUM_CONTAIN
import com.example.initialdataentry.Constants.Companion.STS_CONTAIN
import com.example.initialdataentry.Constants.Companion.VU_CONTAIN
import com.example.initialdataentry.R
import com.example.initialdataentry.classes.SharedPreference
import com.example.initialdataentry.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var sharedPreference: SharedPreference
    private lateinit var binding: FragmentResultBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sharedPreference = SharedPreference(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.grzTVData.text = sharedPreference.getShared(CAR_NUM_CONTAIN)
        binding.stsTVData.text = sharedPreference.getShared(STS_CONTAIN)
        binding.vuTVData.text = sharedPreference.getShared(VU_CONTAIN)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultFragment()
    }
}