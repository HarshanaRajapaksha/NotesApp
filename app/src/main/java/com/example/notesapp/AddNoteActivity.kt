package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.notesapp.Models.Note
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            binding.edtTitle.setText(old_note.title)
            binding.edtNote.setText(old_note.note)
            isUpdate = true

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {

            var title = binding.edtTitle.text.toString()
            var note_desc = binding.edtNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {

                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate) {
                    note = Note(old_note.id, title, note_desc, formatter.format(Date()))
                } else {
                    note = Note(null, title, note_desc, formatter.format(Date()))
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this@AddNoteActivity, "Please enter some data", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }

    }
}