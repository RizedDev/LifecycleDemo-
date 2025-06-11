package com.example.lifecycledemo.activities

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifecycledemo.R
import com.example.lifecycledemo.adapters.BookAdapter
import com.example.lifecycledemo.adapters.OnItemClickListener
import com.example.lifecycledemo.constants.APP_LAUNCH_COUNT_KEY
import com.example.lifecycledemo.constants.COUNTER_KEY
import com.example.lifecycledemo.constants.TAG
import com.example.lifecycledemo.data.model.Book
import com.example.lifecycledemo.databinding.ActivityMainBinding
import com.example.lifecycledemo.utils.makeToast
import androidx.core.content.edit
import com.example.lifecycledemo.constants.IS_FIRST_RUN_KEY

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
    private var appLaunchCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate вызван")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("MyAppSettings", Context.MODE_PRIVATE)
        appLaunchCount = sharedPref.getInt(APP_LAUNCH_COUNT_KEY, 0)
        appLaunchCount++
        sharedPref.edit {
            putInt(APP_LAUNCH_COUNT_KEY, appLaunchCount)
        }

        if (sharedPref.getBoolean(IS_FIRST_RUN_KEY, true)) {
            sharedPref.edit {
                putBoolean(IS_FIRST_RUN_KEY, false)
            }
        } else {
            binding.firstAppRun.text = "Бывалый"
        }
        makeToast("Приложение запущено $appLaunchCount раз")

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
                    val deleteBookDialog = AlertDialog.Builder(this)
                    deleteBookDialog.setTitle("Подтверждение удаления")
                    deleteBookDialog.setMessage("Вы уверены, что хотите удалить книгу ${selectedBook.title}?")

                    deleteBookDialog.setPositiveButton("Да") { _, _ ->
                        myBooks.removeAt(position)
                        adapter.notifyItemChanged(position)
                        makeToast("Книга удалена")
                    }

                    deleteBookDialog.setNegativeButton("Нет") {_, _ ->
                        makeToast("Удаление отменено")
                    }

                    deleteBookDialog.show()
                    true
                }
                R.id.menu_return_book -> {
                    makeToast("Книга ${selectedBook.title} возвращена")
                    true
                }
                R.id.menu_edit_year -> {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(this,
                        { _, selectedYear, selectedMonth, selectedDay ->
                            val selectedDate = "$selectedDay.${selectedMonth + 1}.$selectedYear"
                            selectedBook.yearPublished = selectedYear
                            adapter.notifyItemChanged(position)
                            Log.d(TAG, "${myBooks[0]}")
                            makeToast("Выбрана дата: $selectedDate")
                        }, year, month, day)

                    datePickerDialog.show()
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