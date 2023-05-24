package com.project.mindmate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mindmate.Database.JournalDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.mindmate.Adapter.JournalsAdapter
import com.project.mindmate.Models.Journal
import com.project.mindmate.Models.JournalViewModel

class JournalsCrudFragment : Fragment(), JournalsAdapter.JournalsClickListener, PopupMenu.OnMenuItemClickListener{
    lateinit var database: JournalDatabase
    lateinit var viewModel : JournalViewModel
    lateinit var adapter: JournalsAdapter
    lateinit var selectedJournal : Journal
    lateinit var recyclerView : RecyclerView
    lateinit var addFAB : FloatingActionButton
    lateinit var search : SearchView
    private val updateJournal = registerForActivityResult(
        ActivityResultContracts
        .StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val journal = result.data?.getSerializableExtra("journal") as? Journal

            if (journal!=null){
                viewModel.updateJournal(journal)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_journals_crud, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewJournals)
        addFAB = view.findViewById(R.id.addFAB)
        search = view.findViewById(R.id.searchJournals)
        // initializing the UI
        initUI()
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(JournalViewModel::class.java)

        viewModel.allJournals.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = JournalDatabase.getDatabase(requireContext())
        // Inflate the layout for this fragment
        return view
    }
    private fun initUI() {

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false )
        adapter = JournalsAdapter(requireContext(), this)
        recyclerView.adapter = adapter

        val getContent = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val journal = result.data?.getSerializableExtra("journal") as? Journal
                if (journal!=null){
                    viewModel.insertJournal(journal)
                }
            }
        }

        addFAB.setOnClickListener {
            val intent = Intent(requireContext(), AddJournalsActivity::class.java)
            getContent.launch(intent)
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!= null){
                    adapter.filterList(newText)
                }
                return true
            }

        })

    }
    override fun onItemClicked(journal: Journal) {
        val intent = Intent(requireContext(), AddJournalsActivity::class.java)
        intent.putExtra("current_journal", journal)
        updateJournal.launch(intent)
    }

    override fun onLongItemClicked(journal: Journal, cardView: CardView) {
        selectedJournal = journal
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {

        val popup = PopupMenu(requireContext(), cardView)
        popup.setOnMenuItemClickListener(this@JournalsCrudFragment)
        popup.inflate(R.menu.journals_popup)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_journal){
            viewModel.deleteJournal(selectedJournal)
            return true
        }
        return false
    }

}