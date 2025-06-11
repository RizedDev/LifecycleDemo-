package com.example.lifecycledemo.adapters

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifecycledemo.R
import com.example.lifecycledemo.data.model.Book

interface OnItemClickListener {
    fun onClick(book: Book)
}

class BookAdapter(private val bookList: MutableList<Book>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

        var selectedPosition: Int = RecyclerView.NO_POSITION

    class BookViewHolder(item: View) : RecyclerView.ViewHolder(item), View.OnCreateContextMenuListener {
        val title: TextView = item.findViewById(R.id.bookTitleTW)
        val author: TextView = item.findViewById(R.id.bookAuthorTW)

        init {
            item.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle("Действие с книгой")

            menu?.add(0, R.id.menu_borrow_book, 0, "Взять книгу")
            menu?.add(0, R.id.menu_return_book, 1, "Вернуть книгу")
            menu?.add(0, R.id.menu_delete_book, 2, "Удалить книгу")
            menu?.add(0, R.id.menu_edit_year, 3, "Изменить год публикации")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.title.text = book.title
        holder.author.text = book.author

        holder.itemView.setOnClickListener {
            listener.onClick(book)
        }

        holder.itemView.setOnLongClickListener {
            selectedPosition = holder.layoutPosition
            false
        }
    }
}