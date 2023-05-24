package com.project.mindmate

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.project.mindmate.Models.Journal
import java.text.SimpleDateFormat
import java.util.*

class AddJournalsActivity : AppCompatActivity() {

    private lateinit var journal : Journal
    private lateinit var old_journal : Journal
    var isUpdate = false
    private lateinit var etTitle : EditText
    private lateinit var etJournal: EditText
    private lateinit var imgDone : ImageView
    private lateinit var imgBack : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_journals)
        etTitle = findViewById(R.id.etTitle)
        etJournal = findViewById(R.id.etJournal)
        imgBack = findViewById(R.id.imgBack)
        imgDone = findViewById(R.id.imgDone)

        try {

            old_journal = intent.getSerializableExtra("current_journal") as Journal
            etTitle.setText(old_journal.title)
            etJournal.setText(old_journal.journal)
            isUpdate = true

        } catch (e : java.lang.Exception){
            e.printStackTrace()
        }

        imgDone.setOnClickListener {
            val title = etTitle.text.toString()
            val journal_desc = etJournal.text.toString()

            if (title.isNotEmpty() || journal_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate){
                    journal = Journal(
                        old_journal.id, title, journal_desc, formatter.format(Date())
                    )
                } else{
                    journal = Journal(
                        null, title, journal_desc, formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("journal", journal)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}