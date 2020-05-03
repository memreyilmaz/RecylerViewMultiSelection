package com.example.recylerviewmultiselection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = BookAdapter(getBooks())
    }

    fun getBooks(): MutableList<Book> {
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
}
