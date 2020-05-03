package com.example.recylerviewmultiselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book.view.*

class BookAdapter(private val onItemClickListener: (View?, Int) -> Unit?,
                  private val onItemLongClickListener: (View?, Int) -> Unit?,
                  private val onQuantityClickListener: (Book) -> Unit?
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private var isCheckboxVisible = false
    private var currentSelectedIndex = -1
    private val selectedBooksList: MutableMap<Int, Book> = mutableMapOf()
    private var bookList: List<Book> = listOf()

    fun setBookList(bookList: List<Book>) {
        this.bookList = bookList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView)
    }
    
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
        toggleSelectedItem(holder, position)
    }
    
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val nameText: TextView = itemView.findViewById(R.id.nametext)
        private val priceText: TextView = itemView.findViewById(R.id.pricetext)
        private val quantityButton: TextView = itemView.findViewById(R.id.quantitybutton)

        fun bind(item: Book) {
            image.setImageResource(item.cover)
            nameText.text = item.name
            priceText.text = "$" + item.price.toString()
            quantityButton.text = item.quantity.toString()

            if (isCheckboxVisible) {
                checkbox.visibility = View.VISIBLE
            } else {
                checkbox.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onItemClickListener.invoke(it, layoutPosition)
            }

            itemView.setOnLongClickListener {
                onItemLongClickListener.invoke(it, layoutPosition)
                true
            }
            
            itemView.quantitybutton.setOnClickListener {
                onQuantityClickListener.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun changeCheckboxVisibility() {
        isCheckboxVisible = !isCheckboxVisible
        notifyDataSetChanged()
    }

    private fun toggleSelectedItem(holder: BookViewHolder, position: Int) {
        if (currentSelectedIndex == position) resetCurrentIndex()
        if (selectedBooksList.contains(position)) {
            holder.itemView.setBackgroundResource(R.color.colorLightGray)
            holder.itemView.checkbox.isChecked = true
        } else {
            holder.itemView.setBackgroundResource(android.R.color.transparent)
            holder.itemView.checkbox.isChecked = false
        }
    }

    private fun resetCurrentIndex() {
        currentSelectedIndex = -1
    }

    fun getCartTotalSum(): String {
        var cartTotalSum = 0.0
        for (item in selectedBooksList.values) {
            cartTotalSum += item.price * item.quantity
        }
        return "$cartTotalSum"
    }

    fun getSelectedBooksCount(): Int {
        return selectedBooksList.size
    }

    fun clearSelectedBooksList() {
        selectedBooksList.clear()
        notifyDataSetChanged()
    }

    fun deleteSelectedBooks(){
        //todo
    }

    fun toggleSelection(position: Int) {
        currentSelectedIndex = position

        if (selectedBooksList.contains(position)) {
            selectedBooksList.remove(position)
        } else {
            selectedBooksList[position] = bookList[position]
        }
        notifyItemChanged(position)
    }
}