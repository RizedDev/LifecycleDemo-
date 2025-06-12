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
import androidx.lifecycle.lifecycleScope
import com.example.lifecycledemo.constants.IS_DB_POPULATED_KEY
import com.example.lifecycledemo.constants.IS_FIRST_RUN_KEY
import com.example.lifecycledemo.data.dao.BookDao
import com.example.lifecycledemo.data.db.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    private var appLaunchCount: Int = 0
    private lateinit var bookDao: BookDao

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

        val db = AppDatabase.getDatabase(applicationContext)
        bookDao = db.bookDao()

        val isDbPopulated = sharedPref.getBoolean(IS_DB_POPULATED_KEY, false)

        if (!isDbPopulated) {
            lifecycleScope.launch {
                bookDao.insertBook(Book(title = "Война и мир", author = "Л.Н. Толстой", yearPublished = 1869))
                bookDao.insertBook(Book(title = "Мастер и Маргарита", author = "М.А. Булгаков", yearPublished = 1967))
                bookDao.insertBook(Book(title = "Преступление и наказание", author = "Ф.М. Достоевский", yearPublished = 1866))
                bookDao.insertBook(Book(title = "1984", author = "Дж. Оруэлл", yearPublished = 1949))
                bookDao.insertBook(Book(title = "Гордость и предубеждение", author = "Дж. Остин", yearPublished = 1813))

                sharedPref.edit {
                    putBoolean(IS_DB_POPULATED_KEY, true)
                }
            }
        }

        lifecycleScope.launch {
            bookDao.getAllBooks().collect { books ->
                if (binding.bookRecyclerView.adapter == null) {
                    binding.bookRecyclerView.adapter =
                        BookAdapter(books.toMutableList(), this@MainActivity)
                    binding.bookRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                } else {
                    (binding.bookRecyclerView.adapter as BookAdapter).updateList(books.toMutableList())
                }
            }
        }

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
            val selectedBook = adapter.bookList[position]

            return when (item.itemId) {
                R.id.menu_borrow_book -> {
                    makeToast("Книга ${selectedBook.title} взята")
                    true
                }

                R.id.menu_delete_book -> {
                    AlertDialog.Builder(this)
                        .setTitle("Подтверждение удаления")
                        .setMessage("Вы уверены, что хотите удалить книгу ${selectedBook.title}?")
                        .setPositiveButton("Да") { _, _ ->
                            lifecycleScope.launch {
                                bookDao.deleteBook(selectedBook)
                            }
                            makeToast("Книга удалена")
                        }
                        .setNegativeButton("Нет") { _, _ ->
                            makeToast("Удаление отменено")
                        }.show()
                    true
                }

                R.id.menu_return_book -> {
                    makeToast("Книга ${selectedBook.title} возвращена")
                    true
                }

                R.id.menu_edit_year -> {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, selectedBook.yearPublished)
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val datePickerDialog = DatePickerDialog(
                        this,
                        { _, selectedYear, _, _ ->
                            lifecycleScope.launch {
                                val updateBook = selectedBook.copy(yearPublished = selectedYear)
                                bookDao.updateBook(updateBook)
                            }
                            makeToast("Выбрана дата: $selectedYear")
                        }, year, month, day
                    )
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