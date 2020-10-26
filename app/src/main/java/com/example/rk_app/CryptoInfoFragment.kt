package com.example.rk_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat

private const val ARG_LIST_ITEM = "listElem"

class CryptoInfoFragment : Fragment() {
    private var data: DataListItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable<DataListItem>(ARG_LIST_ITEM)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crypto_info, container, false)

        view.findViewById<TextView>(R.id.header).text = "${convertStampToDate(data!!.time)} ${data!!.close} ${data!!.currency}"
        view.findViewById<TextView>(R.id.text_min).text = data!!.low.toString()
        view.findViewById<TextView>(R.id.text_max).text = data!!.high.toString()
        view.findViewById<TextView>(R.id.text_open).text = data!!.open.toString()
        view.findViewById<TextView>(R.id.text_close).text = data!!.close.toString()

        return view
    }

    companion object {

        fun createBundle(data: DataListItem): Bundle {
            val args = Bundle()
            args.putParcelable(ARG_LIST_ITEM, data)
            return args
        }
    }

    fun convertStampToDate(stamp:Int):String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateInst = java.util.Date(stamp.toLong()*1000)
        return sdf.format(dateInst).toString()
    }
}