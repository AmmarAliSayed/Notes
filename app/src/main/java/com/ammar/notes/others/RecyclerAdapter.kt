package com.ammar.notes.others

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ammar.notes.R
import com.ammar.notes.db.NoteModel
import kotlinx.android.synthetic.main.single_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter (private val interaction: Interaction? = null): RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    val DIFF_CALLBACK = object: DiffUtil.ItemCallback<NoteModel>(){
        //Called to check whether two objects represent the same item.so we do not need compare all data
        //we can compare primary key (if you use database) because PK is unique for that object
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id==newItem.id
        }
        //Called to check whether two items have the same data.so compare the old (image,title,author)
        // to the new item (image,title,author)
        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return  oldItem==newItem
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false), interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // use when ->when you have different holders type in the project
        when(holder) {
            is NoteViewHolder -> {
                holder.bind(differ.currentList[position])
            }

        }
    }
    override fun getItemCount(): Int {
        // return items.size
        return  differ.currentList.size
    }

    fun getNoteAt(position: Int):NoteModel{
        return differ.currentList[position]
    }

    fun submitList(notes: List<NoteModel>){
        // submitList() :fun in the AsyncListDiffer used to submitting new list items to current list
        differ.submitList(notes)
    }

    //custom ViewHolder
    class NoteViewHolder (itemView: View, private val interaction: Interaction?): RecyclerView.ViewHolder( itemView){


        //the  bind fun do the setting operation and this fun is not built in fun
        fun bind(note: NoteModel) = with(itemView){
            itemView.tv_title_id.text=note.title
            itemView.tv_note_id.text=note.description
            itemView.tv_date.text=note.timestamp

            itemView.setOnClickListener {
                //use  getAdapterPosition() to detect the position of the item that was clicked
                interaction?.onItemSelected(adapterPosition, note)
            }

        }

    }
    // interface for detecting items clicks .take the position of the item you selected and the actual object that was clicked
    // this is a common pattern to handle clicks in every recyclerView
    interface Interaction {
        fun onItemSelected(position: Int, item: NoteModel)
    }


}