package gq.trtechlesson.notes

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import android.view.LayoutInflater

class NoteRVAdapter(private val context: Context, private val Listener : INotesRVAdapter): RecyclerView.Adapter<NoteRVAdapter.NoteRVViewHolder>() {

    private val allNotes : ArrayList<Note> = ArrayList()

    inner class NoteRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val noteText : TextView = itemView.findViewById(R.id.text)
        val deleteBtn : ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRVViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent,false)
        val viewHolder = NoteRVViewHolder(view)

        viewHolder.deleteBtn.setOnClickListener {
            Listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteRVViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.noteText.text = currentNote.text
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }

}

interface INotesRVAdapter {
    fun onItemClicked(note: Note)
}