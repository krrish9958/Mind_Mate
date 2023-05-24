package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.mindmate.Adapter.CommentAdapter
import com.project.mindmate.Models.CommentModel

class DetailedPostActivity : AppCompatActivity() {
    private lateinit var detailPostTv : TextView
    private lateinit var postComment : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var dateTv : TextView
    private lateinit var commentList: ArrayList<CommentModel>
    private lateinit var adapter: CommentAdapter
    private lateinit var addComment : EditText
    private lateinit var imgBack : ImageView
    private lateinit var deleteTv : TextView
    private lateinit var dbReferencePost: DatabaseReference
    private lateinit var dbReferenceComment: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_post)
        commentList = arrayListOf<CommentModel>()
        detailPostTv = findViewById(R.id.postDetailTv)
        postComment = findViewById(R.id.postComment)
        recyclerView = findViewById(R.id.detailedPostRv)
        addComment = findViewById(R.id.addCmntEt)
        dateTv = findViewById(R.id.datePostTv)
        imgBack = findViewById(R.id.backBtnPost)
        deleteTv = findViewById(R.id.deleteTv)

        detailPostTv.text = intent.getStringExtra("postText")
        val postId = intent.getStringExtra("postId")

        dateTv.text = intent.getStringExtra("postDate")

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        postComment.setOnClickListener {
            commentDataSave()
        }
        imgBack.setOnClickListener {
            onBackPressed()
        }

        deleteTv.setOnClickListener {
//            deleteRecord(postId)
            Toast.makeText(this, "This is disabled, we're working on it! Hang tight", Toast.LENGTH_SHORT).show()
        }

        dbReferencePost = FirebaseDatabase.getInstance().getReference("Posts/$postId")
        dbReferenceComment = dbReferencePost.child("Comments")
        getCommentsData()
    }

    private fun deleteRecord(postId: String?) {
        if (postId != null) {
            dbReferencePost.child(postId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists() && snapshot.child("author").value == FirebaseAuth.getInstance().currentUser?.uid) {
                        dbReferencePost.child(postId).removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(this@DetailedPostActivity, "Post deleted successfully!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@DetailedPostActivity, HomeActivity::class.java)
                                intent.putExtra("explore_fragment", "explore_fragment_tag")
                                finish()
                                startActivity(intent)
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(this@DetailedPostActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                    else {
                        Toast.makeText(this@DetailedPostActivity, "You are not authorized to delete this post!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DetailedPostActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun commentDataSave() {
        val commentText = addComment.text.toString()
        if (commentText.isEmpty()){
            addComment.error = "Enter a comment to post"
        }
        val commentId = dbReferenceComment.push().key!!
        val comment = CommentModel(commentId,commentText)
        dbReferenceComment.child(commentId).setValue(comment)
            .addOnSuccessListener {
                addComment.text.clear()
                Toast.makeText(this, "Comment posted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Error occurred : ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getCommentsData() {
        dbReferenceComment.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                commentList.clear()
                if (snapshot.exists()){
                    for (commentSnap in snapshot.children){
                        val commentData = commentSnap.getValue(CommentModel::class.java)
                        commentList.add(commentData!!)
                    }
                    adapter = CommentAdapter(commentList)
                    recyclerView.adapter = adapter
                    recyclerView.isNestedScrollingEnabled = true

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}