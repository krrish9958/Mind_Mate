package com.project.mindmate

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    private lateinit var datePickerEt: TextView
    private lateinit var datePickerBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        datePickerEt = findViewById(R.id.dobEt)
        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            upDateLable(myCalender)
        }

        datePickerBtn = findViewById(R.id.btnDatePicker)
        datePickerBtn.setOnClickListener {
            DatePickerDialog(
                this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(
                    Calendar.MONTH
                ), myCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun upDateLable(myCalender: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        datePickerEt.setText(sdf.format(myCalender.time))
    }
}