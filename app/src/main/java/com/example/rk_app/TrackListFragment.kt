package com.example.rk_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<TrackListAdapter.ElementViewHolder>

    private lateinit var crypto : String
    private lateinit var money : String
    private lateinit var limit : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewAdapter = TrackListAdapter(requireContext())

        crypto = "BTC"
        money = "USD"
        limit = "15"

        getCurrency()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id==R.id.refresh) {
            crypto = requireView().findViewById<EditText>(R.id.currencySelected).text.toString()
            println(crypto)
            getCurrency()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_list, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.trackByDays).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        return view
    }

    private fun getCurrency(){
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