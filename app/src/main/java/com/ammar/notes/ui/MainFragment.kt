package com.ammar.notes.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ammar.notes.R
import com.ammar.notes.db.NoteDatabase
import com.ammar.notes.db.NoteModel
import com.ammar.notes.db.repositories.NoteRepository
import com.ammar.notes.others.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main.*



class MainFragment : Fragment() ,RecyclerAdapter.Interaction{
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var navController: NavController
    private lateinit var   bundle:Bundle
    lateinit var database: NoteDatabase
    lateinit var  repository: NoteRepository
    lateinit var  factory:NoteViewModelFactory
    lateinit var  viewModel: NoteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        initRecyclerView()
        deleteItemOnSwiped()
        //to show Options menu items in fragment used this method 1
        setHasOptionsMenu(true)

         database= NoteDatabase(requireContext())
         repository= NoteRepository(database)
        factory=NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(),factory).get(NoteViewModel::class.java)


        fab_btn.setOnClickListener {
            //if id=0 then this insert new item operation
            bundle = bundleOf("key_id" to 0)
            navController.navigate(R.id.action_mainFragment_to_secondFragment,bundle)
        }

        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer {
            // Log.i("tag","${it.toString()}")
            recyclerAdapter.submitList(it)
        })



        }
    private fun initRecyclerView(){

        RecyclerView_id.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerAdapter = RecyclerAdapter(this@MainFragment)
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: NoteModel) {
        //if id=i then this a edit  item operation
        bundle = bundleOf("key_id" to 1,"row_pk" to item.id)
        navController.navigate(R.id.action_mainFragment_to_secondFragment,bundle)


    }

    private  fun deleteItemOnSwiped() {
        //Create the callback and tell it what events to listen for.
        //It takes two parameters: One for drag directions and one for swipe directions.
        //You’re only interested in swipe. Pass 0 to inform the callback not to respond to drag events.
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder2: RecyclerView.ViewHolder): Boolean {
               // Return false in onMove. You don’t want to perform any special behavior here.
                return false
            }
            //-Call onSwiped when you swipe an item in the direction specified in the ItemTouchHelper. Here,
            // you request the viewHolder parameter passed for the position of the item view,
            //   and then you remove that item from your list . Finally,
            //   you inform the RecyclerView adapter that an item has been removed at a specific position.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                viewModel.delete(recyclerAdapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "item deleted", Toast.LENGTH_SHORT).show()
            }
            }
            //Initialize ItemTouchHelper with the callback behavior you defined, and then attach it to the RecyclerView.
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView( RecyclerView_id)

    }


//to show  Options menu items in fragment used this method 2
override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.main_menu,menu)
}
    //handle  Options menu items clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAll_id ->{
                viewModel.clearAll()
            }
        }

        return true
    }
}
