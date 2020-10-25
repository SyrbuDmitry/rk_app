package com.example.rk_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var crypto : String
    private lateinit var money : String
    private lateinit var limit : String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu,menu);
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        val id: Int = item.itemId
        if (id==R.id.refresh){
            crypto = findViewById<EditText>(R.id.currencySelected).text.toString()
            println(crypto)
            currencyRequest()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecycler(){
        viewManager = LinearLayoutManager(this)
        viewAdapter = TrackListAdapter(applicationContext)
        (viewAdapter as TrackListAdapter).data = listOf()
        crypto = "BTC"
        money = "USD"
        limit = "15"
        currencyRequest()
        recyclerView = findViewById<RecyclerView>(R.id.trackByDays).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun currencyRequest(){
        val apiRes = API.retrofitService.getData(crypto, money,limit);
        apiRes.enqueue( object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>?) {
                if(response?.body() != null) {
                    val dataList = response.body()!!.Data.DataList
                    dataList.forEach{
                        it.currency = crypto
                    }
                    (viewAdapter as TrackListAdapter).data = dataList.reversed()
                    (viewAdapter as TrackListAdapter).notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
                println("ERROR")
                println(t)
            }
        })
    }
}