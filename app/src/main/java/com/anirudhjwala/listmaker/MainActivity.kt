package com.anirudhjwala.listmaker

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val listDataManager: ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lists = listDataManager.readLists()

        todoListRecyclerView = findViewById(R.id.list_recyclerview)
        // Vertically we align the item in linear fashion
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        // Assigning the adapter to recycler view
        todoListRecyclerView.adapter = TodoListAdapter(lists)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }
    }

    private fun showCreateTodoListDialog() {

        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)

        todoTitleEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val adapter = todoListRecyclerView.adapter as TodoListAdapter
            val list = TaskList(todoTitleEditText.text.toString())
            listDataManager.saveList(list)
            adapter.addList(list)

            dialog.dismiss()
        }
        myDialog.create().show()

    }

}
