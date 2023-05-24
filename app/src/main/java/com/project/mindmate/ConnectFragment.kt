package com.project.mindmate

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.mindmate.Adapter.PostAdapter
import com.project.mindmate.Models.PostModel
import java.text.SimpleDateFormat
import java.util.Date

class ConnectFragment : Fragment() {
    private lateinit var etPost: EditText
    private lateinit var postTv: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var postList: ArrayList<PostModel>
    private lateinit var adapter: PostAdapter
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_connect, container, false)
        etPost = view.findViewById(R.id.etPost)
        postTv = view.findViewById(R.id.postTv)
        recyclerView = view.findViewById(R.id.exploreRecyclerView)
        dbReference = FirebaseDatabase.getInstance().getReference("Posts")
        postList = arrayListOf<PostModel>()
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.scrollToPosition(0)
        postTv.setOnClickListener {
            savePostsData()
        }
        getPostsData()
        return view
    }

    private fun savePostsData() {
        val postText = etPost.text.toString()
        val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
        val postDate = formatter.format(Date())
        if (postText.isEmpty()) {
            etPost.error = "Enter something!"
        }

        val postId = dbReference.push().key!!
        val post = PostModel(postId, postText, postDate)
        dbReference.child(postId).setValue(post)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Posted Successfully", Toast.LENGTH_SHORT).show()
                etPost.text.clear()
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    requireContext(),
                    "Error occured : ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun getPostsData() {
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                if (snapshot.exists()) {
                    for (postSnap in snapshot.children) {
                        val postData = postSnap.getValue(PostModel::class.java)
                        postList.add(postData!!)
                    }
                    postList.reverse()
                    adapter = PostAdapter(postList)
                    recyclerView.adapter = adapter


                    adapter.setOnItemClickListener(object : PostAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireActivity(), DetailedPostActivity::class.java)
                            intent.putExtra("postId", postList[position].postId)
                            intent.putExtra("postText", postList[position].postText)
                            intent.putExtra("postDate", postList[position].date)
                            requireActivity().startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }


}