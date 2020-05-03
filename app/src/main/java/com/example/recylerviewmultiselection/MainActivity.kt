package com.example.recylerviewmultiselection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var actionMode: ActionMode? = null
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bookAdapter = BookAdapter(onItemClickListener,onItemLongClickListener, onQuantityClickListener)
        bookAdapter.setBookList(getBooks())
        recyclerview.adapter = bookAdapter
    }

    private fun getBooks(): MutableList<Book> {
        return mutableListOf(
            Book(id = 1,cover = R.drawable.annakarenina,quantity = 1, name = "Anna Karenina", price = 13.45),
            Book(id = 2,cover = R.drawable.madamebovary,quantity = 2, name = "Madame Bovary", price = 4.99),
            Book(id = 3,cover = R.drawable.warandpeace,quantity = 1, name = "War and Peace", price = 13.22),
            Book(id = 4,cover = R.drawable.lolita,quantity = 1, name = "Lolita", price = 11.12),
            Book(id = 5,cover = R.drawable.theadventuresofhuckleberryfinn,quantity = 3, name = "The Adventures of Huckleberry Finn", price = 3.95),
            Book(id = 6,cover = R.drawable.hamlet,quantity = 2, name = "Hamlet", price = 4.55),
            Book(id = 7,cover = R.drawable.thegreatgatsby,quantity = 1, name = "The Great Gatsby", price = 8.00),
            Book(id = 8,cover = R.drawable.insearchoflosttime,quantity = 1, name = "In Search of Lost Time", price = 47.80),
            Book(id = 9,cover = R.drawable.thestoriesofantonchekhov,quantity = 1, name = "The Stories of Anton Chekhov", price = 12.10),
            Book(id = 10,cover = R.drawable.middlemarch,quantity = 5, name = "Middlemarch", price = 7.99)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_edit -> {
                if (actionMode == null) {
                    actionMode = this.startActionMode(ActionModeCallback())
                    bookAdapter.changeCheckboxVisibility()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    open inner class ActionModeCallback : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            val count = bookAdapter.getSelectedBooksCount()
            when (item?.itemId) {
                R.id.action_delete_from_cart -> {
                    if (count > 0) {
                        bookAdapter.deleteSelectedBooks()
                        Toast.makeText(
                            this@MainActivity,
                            count.toString() + "Item(s) Deleted From Your Cart",
                            Toast.LENGTH_SHORT
                        ).show()
                        mode?.finish()
                    }
                    return true
                }
                R.id.action_add_to_favourites_list -> {
                    if (count > 0) {
                        Toast.makeText(
                            this@MainActivity,
                            count.toString() + "Item(s) Added To Your Favourites List",
                            Toast.LENGTH_SHORT
                        ).show()
                        mode?.finish()
                    }
                    return true
                }
            }
            return true
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_action_mode, menu)
            mode?.title = "Select"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            bookAdapter.changeCheckboxVisibility()
            bookAdapter.clearSelectedBooksList()
            actionMode?.subtitle = ""
            actionMode = null
        }
    }

    private val onItemClickListener = { view: View?, position: Int ->
        if (actionMode != null) {
            toggleSelection(position)
        }
    }

    private val onItemLongClickListener = { view: View?, position: Int ->
        if (actionMode != null) {
            toggleSelection(position)
        } else {
            actionMode = this.startActionMode(ActionModeCallback())
            bookAdapter.changeCheckboxVisibility()
            toggleSelection(position)
        }
    }

    private val onQuantityClickListener: ((Book) -> Unit) = {book->
        Toast.makeText(
            this@MainActivity,
            book.name +" Quantity Button Clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun toggleSelection(position: Int) {
        bookAdapter.toggleSelection(position)
        val count: Int = bookAdapter.getSelectedBooksCount()
        if (count == 0) {
            actionMode?.finish()
        } else {
            actionMode?.apply {
                title = resources.getQuantityString(R.plurals.title_action_bar_item_selected, count, count)
                menu.findItem(R.id.action_delete_from_cart).setIcon(R.drawable.ic_delete_active)
                menu.findItem(R.id.action_add_to_favourites_list).setIcon(R.drawable.ic_favorite_active)
                subtitle = getString(R.string.cart_sum, bookAdapter.getCartTotalSum())
                invalidate()
            }
        }
    }
}
