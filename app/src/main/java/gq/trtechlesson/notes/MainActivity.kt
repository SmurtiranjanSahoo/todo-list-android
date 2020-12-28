package gq.trtechlesson.notes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NoteRVAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }

        })

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
    }

    fun submitData(view: View) {
        val noteText = input.editableText.toString()
        if (noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
            input.text = null
            hideKeyboard(view)
        }
    }

    fun hideKeyboard(view: View) {
        try {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (ignored: Exception) {
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // menu bar icon handling

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {

        R.id.menu_about -> {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.menu_share -> {
            val appUrl = "https://github.com/SmurtiranjanSahoo/MemeShare"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type ="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Checkout This Amazing Todo App $appUrl")
            val chooser = Intent.createChooser(intent, "Share this App Using ...")
            startActivity(chooser)
            true
        }
        else -> super.onOptionsItemSelected(item)

    }

}