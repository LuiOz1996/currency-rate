package com.example.tt_exchangerate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter (
    Context: Context, private val course : List<DataCourse>,
    private val onClickListener: CourseOnClickListener  ) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(Context)
    override fun getItemCount(): Int = course.size
    private fun getItem(position: Int):DataCourse = course[position]

    class ViewHolder (itemView : View)
        : RecyclerView.ViewHolder(itemView) {
        private val abbreviation : TextView = itemView.findViewById(R.id.abbreviation)
        private val decryption :TextView = itemView.findViewById(R.id.decryption)
        private val course_now : TextView = itemView.findViewById(R.id.course_now)
        private val course_last : TextView = itemView.findViewById(R.id.course_last)

        fun bind (version: DataCourse){
            abbreviation.text = version.abbreviationName
            decryption.text = version.decryptionName
            course_now.text = version.course_nowNumber.toString()
            course_last.text = version.course_lastNumber.toString()
        }
    }


    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_for_exchange,parent,false))
    }

}