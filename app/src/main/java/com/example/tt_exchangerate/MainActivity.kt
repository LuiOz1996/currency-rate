package com.example.tt_exchangerate

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_conversion.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(), CourseOnClickListener {

    lateinit var pDialog: ProgressDialog
    val list = ArrayList<DataCourse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://www.cbr-xml-daily.ru/daily_json.js"

        button_To_Home.setBackgroundResource(R.drawable.home_in)
        button_To_Conversion.setBackgroundResource(R.drawable.conversion)
        AsyncTaskHandler().execute(url)

        button_To_Conversion.setOnClickListener{
            val Intent = Intent(this, Conversion::class.java)
            startActivity(Intent)
        }



    }

    inner class AsyncTaskHandler : AsyncTask<String,String,String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog.setMessage("Please Wait")
            pDialog.setCancelable(false)
            pDialog.show()
        }

        override fun doInBackground(vararg url: String?): String {
            val response : String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                response = connection.inputStream.use { it.reader().use { reader -> reader.readText()}}
            }
            finally {
                connection.disconnect()
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (pDialog.isShowing()) pDialog.dismiss()
            jsonResult(result)
        }

        private fun jsonResult (jsonStringer: String?){

            val jsonResponse : JSONObject = JSONObject(jsonStringer)
            val jsonValue : JSONObject = jsonResponse.getJSONObject("Valute")
            val bufferArray : Array<String> = arrayOf("AUD","AZN","GBP","AMD","BYN","BGN","BRL","HUF","HKD",
                                                        "DKK","USD","EUR","INR","KZT","CAD","KGS","CNY","MDL",
                                                        "NOK","PLN","RON","XDR","SGD","TJS","TRY","TMT","UZS",
                                                        "UAH","CZK","SEK","CHF","ZAR","KRW","JPY")
            val buffer = bufferArray.size
            var i = 0
            while (i <buffer){
                val jsonObject = jsonValue.getJSONObject(bufferArray[i])
                list.add (
                            DataCourse(
                                jsonObject.getString("CharCode"),
                                jsonObject.getString("Name"),
                                jsonObject.getDouble("Value"),
                                jsonObject.getDouble("Previous")
                            )
                        )
                i++
            }

            val adapter = Adapter(this@MainActivity,list,this@MainActivity)
            val recycler = findViewById<RecyclerView>(R.id.recycle_exchange)
            recycler.adapter = adapter

        }


    }

    override fun onClick(position: Int) {
        val intent = Intent(this,Conversion::class.java)
        intent.putExtra("abbreviation", list[position].toString())
        startActivity(intent)
    }

}