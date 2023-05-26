package com.project.mindmate

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileFragment : Fragment() {
    private lateinit var datePickeEt : TextView
    private lateinit var datePickerBtn : ImageView
    private lateinit var logoutBtn : Button
    private lateinit var editProfile : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setcontentView(R.layout.fragment_profile)

    }

    private fun setcontentView(fragmentProfile: Int) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfile = view.findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

//        datePickeEt = view.findViewById(R.id.dobEt)
//        val myCalender = Calendar.getInstance()
//        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//            myCalender.set(Calendar.YEAR , year)
//            myCalender.set(Calendar.MONTH , month)
//            myCalender.set(Calendar.DAY_OF_MONTH , dayOfMonth)
//            upDateLable(myCalender)
//        }
//
//        datePickerBtn = view.findViewById(R.id.btnDatePicker)
//        datePickerBtn.setOnClickListener {
//            DatePickerDialog(requireContext(),datePicker,myCalender.get(Calendar.YEAR),myCalender.get(
//                Calendar.MONTH
//            ),myCalender.get(Calendar.DAY_OF_MONTH)).show()
//        }
//
        logoutBtn = view.findViewById(R.id.logoutBtnProfile)
        logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@ProfileFragment.requireContext(), LoggedOutActivity::class.java))
        }
    }

//    private fun upDateLable(myCalender: Calendar) {
//        val myFormat = "dd-MM-yyyy"
//        val sdf = SimpleDateFormat(myFormat , Locale.UK)
//        datePickeEt.setText(sdf.format(myCalender.time))
//    }
}