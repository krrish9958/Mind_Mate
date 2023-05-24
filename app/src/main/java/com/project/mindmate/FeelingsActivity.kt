package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.mindmate.Models.MoodModel
import com.project.mindmate.databinding.ActivityFeelingsBinding
import com.project.mindmate.utilities.ANGRY_EMOJI
import com.project.mindmate.utilities.CRYING_EMOJI
import com.project.mindmate.utilities.DEPRESSED_EMOJI
import com.project.mindmate.utilities.HAPPY_EMOJI
import com.project.mindmate.utilities.IDK_EMOJI
import com.project.mindmate.utilities.MOOD_SWINGS
import com.project.mindmate.utilities.NEUTRAL_EMOJI
import com.project.mindmate.utilities.RELEIVED_EMOJI
import com.project.mindmate.utilities.SAD_EMOJI
import com.project.mindmate.utilities.SCARED_EMOJI
import com.project.mindmate.utilities.TIRED_EMOJI

class FeelingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeelingsBinding
    private lateinit var happyKeywords: ArrayList<String>
    private lateinit var sadKeywords: ArrayList<String>
    private lateinit var angryKeywords: ArrayList<String>
    private lateinit var depressedKeywords: ArrayList<String>
    private lateinit var anxiousKeywords: ArrayList<String>
    private lateinit var scaredKeywords: ArrayList<String>
    private lateinit var tiredKeywords: ArrayList<String>
    private lateinit var cryingKeywords: ArrayList<String>
    private lateinit var mehKeywords: ArrayList<String>
    private lateinit var relievedKeywords: ArrayList<String>
    private lateinit var swingsKeywords: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeelingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName.toString()
        // getting only the first name of the user
        val userNameParts = userName?.split(" ")
        val firstName = userNameParts?.get(0)
        binding.usernameFeelings.text = "Hello ${firstName}"



        binding.navigateToHomeBtn2.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            //
        }

        binding.etMood.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                keywordsDefinition()
                detectMood()
            }
        })

        binding.saveMood.setOnClickListener {
            saveMoodData()
        }
    }

    private fun saveMoodData() {
        val userMood = binding.etMood.text.toString()
        val firestore = FirebaseFirestore.getInstance()
        val timestamp = Timestamp.now()
        val mood = MoodModel(userMood, timestamp)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userUid = user.uid

            val usersMoodCollection = firestore.collection("users")
                .document(userUid).collection("moods")

            usersMoodCollection.add(mood).addOnSuccessListener {
                Toast.makeText(this, "Mood saved successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@FeelingsActivity, HomeActivity::class.java))
            }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error : ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun keywordsDefinition() {
        happyKeywords = arrayListOf(
            "happy", "smiling", "pleased", "cheerful", "merry", "delighted", "peaceful", "elated",
            "lively", "pleasant", "ecstatic", "content", "grateful", "radiant", "amused", "great"
        )

        sadKeywords = arrayListOf(
            "sad",
            "sorry",
            "unhappy",
            "mournful",
            "heartbroken",
            "cheerless",
            "grief",
            "hurting",
            "hurt",
            "gloomy",
            "dull",
            "low",
            "troubled",
            "sick at heart",
            "not happy",
            "down",
            "overwhelmed",
            "upset",
            "blue",
            "miserable",
            "distressed",
            "heavy hearted",
            "tearful",
            "frustrated"
        )

        angryKeywords = arrayListOf(
            "annoyed",
            "angry",
            "offended",
            "bitter",
            "enraged",
            "furious",
            "heated",
            "irritated",
            "resentful",
            "mad",
            "uptight",
            "irritable",
            "irritating",
            "pissed",
            "provoked",
            "sulking",
            "raging",
            "fuming",
            " hateful"
        )

        depressedKeywords = arrayListOf(
            "depressed",
            "hopeless",
            "numb",
            "isolated",
            "alone",
            "joyless",
            "defeated",
            "discouraged",
            "disheartened",
            "lifeless",
            "empty",
            "null",
            "nothing",
            "despairing",
            "depress",
            "depression",
            "depressing"
        )

        anxiousKeywords = arrayListOf(
            "anxious",
            "nervous",
            "anxiety",
            "worried",
            "uneasy",
            "tense",
            "restless",
            "stressed",
            "panicky",
            "panic",
            "unsettled",
            "insecure"
        )

        scaredKeywords = arrayListOf(
            "fearful",
            "scared",
            "terrified",
            "horror",
            "frightened",
            "startled",
            "shaken",
            "horror stricken"
        )

        tiredKeywords = arrayListOf(
            "tired", "sleepy", "fatigued", "exhausted", "drained", "weary", "lethargic", "worn out",
            "ready to drop", "burnt out", "heavy eyed"
        )

        cryingKeywords = arrayListOf(
            "crying",
            "weeping",
            "sobbing",
            "wailing",
            "bawling",
            "emotional",
            "pained",
            "agony",
            "misery",
            "woe"
        )
        mehKeywords = arrayListOf(
            "meh", "blah", "don't know", "bored", "unimpressed"
        )

        relievedKeywords = arrayListOf(
            "relieved", "calm", "thankful", "released", "eased", "soothed", "glad"
        )

        swingsKeywords = arrayListOf(
            "mood swings"
        )
    }

    private fun detectMood() {
        val userMood = binding.etMood.text.toString().toLowerCase().trim()

        if (happyKeywords.contains(userMood)) {

            binding.tvEmoji.text = HAPPY_EMOJI
        } else if (sadKeywords.contains(userMood)) {

            binding.tvEmoji.text = SAD_EMOJI
        } else if (angryKeywords.contains(userMood)) {

            binding.tvEmoji.text = ANGRY_EMOJI
        } else if (depressedKeywords.contains(userMood)) {

            binding.tvEmoji.text = DEPRESSED_EMOJI
        } else if (anxiousKeywords.contains(userMood)) {

            binding.tvEmoji.text = NEUTRAL_EMOJI
        } else if (scaredKeywords.contains(userMood)) {
            binding.tvEmoji.text = SCARED_EMOJI
        } else if (tiredKeywords.contains(userMood)) {

            binding.tvEmoji.text = TIRED_EMOJI
        } else if (cryingKeywords.contains(userMood)) {

            binding.tvEmoji.text = CRYING_EMOJI
        } else if (mehKeywords.contains(userMood)) {

            binding.tvEmoji.text = IDK_EMOJI
        } else if (relievedKeywords.contains(userMood)) {

            binding.tvEmoji.text = RELEIVED_EMOJI
        } else if (swingsKeywords.contains(userMood)) {

            binding.tvEmoji.text = MOOD_SWINGS
        } else {
            binding.tvEmoji.text = ""
        }
    }
}