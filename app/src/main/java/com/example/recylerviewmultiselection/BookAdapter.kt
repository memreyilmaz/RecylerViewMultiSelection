package com.example.recylerviewmultiselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(val bookList: MutableList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindItems(bookList.get(position))
    }
    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.checkbox)
        val image: ImageView = view.findViewById(R.id.image)
        val nametext: TextView = view.findViewById(R.id.nametext)

        val pricetext: TextView = view.findViewById(R.id.pricetext)
        val quantitybutton: TextView = view.findViewById(R.id.quantitybutton)

        fun bindItems(item: Book) {
            image.setImageResource(item.cover)
            nametext.text = item.name
            pricetext.text = "$" + item.price.toString()
            quantitybutton.text = item.quantity.toString()
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}