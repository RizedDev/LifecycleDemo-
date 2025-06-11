package com.example.lifecycledemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifecycledemo.R
import com.example.lifecycledemo.data.model.Book

class BookAdapter(private val bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.bookTitleTW)
        val author: TextView = item.findViewById(R.id.bookAuthorTW)
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

        }
    }
}