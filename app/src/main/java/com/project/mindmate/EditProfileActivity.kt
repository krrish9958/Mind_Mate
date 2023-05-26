package com.project.mindmate

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    private lateinit var datePickerEt: TextView
    private lateinit var datePickerBtn: ImageView
    private lateinit var backBtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val items = listOf("Male","Female")
        val gender : AutoCompleteTextView = findViewById(R.id.gender)
        val adapter = ArrayAdapter(this , R.layout.list_item,items)
        gender.setAdapter(adapter)
        gender.onItemClickListener = AdapterView.OnItemClickListener{adapterView,view , i , l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "$itemSelected",Toast.LENGTH_SHORT).show()
        }



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

        backBtn=findViewById(R.id.backBtnEp)
        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun upDateLable(myCalender: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        datePickerEt.setText(sdf.format(myCalender.time))
    }
}