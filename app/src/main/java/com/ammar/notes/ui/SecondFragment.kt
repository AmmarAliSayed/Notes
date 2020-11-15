package com.ammar.notes.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ammar.notes.R
import com.ammar.notes.db.NoteDatabase
import com.ammar.notes.db.NoteModel
import com.ammar.notes.db.repositories.NoteRepository
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_second.*
import java.text.SimpleDateFormat
import java.util.*

class SecondFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var database: NoteDatabase
    lateinit var  repository: NoteRepository
    lateinit var  factory:NoteViewModelFactory
    lateinit var  viewModel: NoteViewModel
    private  var id:Int?=null
    private  var pk:Int?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController= Navigation.findNavController(view)
        //to show Options menu items in fragment used this method 1
        setHasOptionsMenu(true)

        database= NoteDatabase(requireContext())
        repository= NoteRepository(database)
        factory=NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(NoteViewModel::class.java)

        id = requireArguments().getInt("key_id")!!
        pk=requireArguments().getInt("row_pk")
    }


    //to show  Options menu items in fragment used this method 2
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }
    //handle  Options menu items clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save_id ->{
                val title = edit_title.text.toString()
                val description = edit_note.text.toString()
                if(title.isNotEmpty() && description.isNotEmpty() && id==0) {
                    val item = NoteModel(0,title,description,getCurrentDate())
                    viewModel.insert(item)
                    navController.navigate(R.id.action_secondFragment_to_mainFragment)
                    Toast.makeText(activity, "saved", Toast.LENGTH_SHORT).show()
                }else  if(title.isNotEmpty() && description.isNotEmpty() && id==1) {
                    val editedItem  = NoteModel(pk!!,title,description,getCurrentDate())
                    viewModel.update(editedItem )
                    navController.navigate(R.id.action_secondFragment_to_mainFragment)
                    Toast.makeText(activity, "updated", Toast.LENGTH_SHORT).show()
                }
            }

        }

        return true
    }

    //method for get current time
    private fun getCurrentDate():String{
        val calender= Calendar.getInstance()
        val mdFormat= SimpleDateFormat("EEEE hh:mm a ")
        val strDate=mdFormat.format(calender.time)
        return strDate
    }


}