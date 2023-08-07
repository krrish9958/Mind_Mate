package com.project.mindmate

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MeditationBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meditation_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numberPicker = view.findViewById<NumberPicker>(R.id.sessionDurationPicker)

        numberPicker.minValue = 1
        numberPicker.maxValue = 120

        val selectedMinutes = numberPicker.value

        view.findViewById<Button>(R.id.startSessionBtn).setOnClickListener {
            val selectedMinutes = numberPicker?.value ?: 10

            val intent = Intent(requireContext(), MeditationTimerActivity::class.java)
            intent.putExtra("DURATION_MIN", selectedMinutes)
            startActivity(intent)
            dismiss()
        }


    }

}