package com.example.lifecycledemo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifecycledemo.R
import com.example.lifecycledemo.adapters.BookAdapter
import com.example.lifecycledemo.adapters.OnItemClickListener
import com.example.lifecycledemo.constants.COUNTER_KEY
import com.example.lifecycledemo.constants.MESSAGE_KEY
import com.example.lifecycledemo.constants.NUMBER_KEY
import com.example.lifecycledemo.constants.TAG
import com.example.lifecycledemo.data.model.Book
import com.example.lifecycledemo.databinding.ActivityMainBinding
import com.example.lifecycledemo.utils.makeToast

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    private var myBooks = mutableListOf(
        Book(1, "Война и мир", "Л.Н. Толстой", 1869),
        Book(2, "Мастер и Маргарита", "М.А. Булгаков", 1967),
        Book(3, "Преступление и наказание", "Ф.М. Достоевский", 1866),
        Book(4, "1984", "Дж. Оруэлл", 1949),
        Book(5, "Гордость и предубеждение", "Дж. Остин", 1813)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate вызван")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER_KEY)
        }
        Log.d(TAG, "значение востановлено")

        val adapter = BookAdapter(myBooks, this)
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter

        initButton(this)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart вызван")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume вызван")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause вызван")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop вызван")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy вызван")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart вызван")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(COUNTER_KEY, counter)
        Log.d(TAG, "значение созранено")
    }

    override fun onClick(book: Book) {
        makeToast("clicked on ${book.title}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val adapter = binding.bookRecyclerView.adapter as BookAdapter
        val position = adapter.selectedPosition

        if (position != RecyclerView.NO_POSITION) {
            val selectedBook = myBooks[position]

            return when (item.itemId) {
                R.id.menu_borrow_book -> {
                    makeToast("Книга ${selectedBook.title} взята")
                    true
                }
                R.id.menu_delete_book -> {
                    myBooks.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    makeToast("Книга ${selectedBook.title} удалена")
                    true
                }
                R.id.menu_return_book -> {
                    makeToast("Книга ${selectedBook.title} возвращена")
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun initButton(activity: Context) {

    }
}