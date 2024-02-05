package com.example.firebase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener


class ViewData : AppCompatActivity() {

    lateinit var rec_data: RecyclerView

    lateinit var adapter: FirebaseRecyclerAdapter<Mydata, ChatHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        rec_data = findViewById(R.id.rec_data)

        val query: Query = FirebaseDatabase.getInstance()
            .reference
            .child("Realtimedataabse")
            .limitToLast(50)

        val options: FirebaseRecyclerOptions<Mydata> = FirebaseRecyclerOptions.Builder<Mydata>()
            .setQuery(query, Mydata::class.java)
            .build()

        adapter =
            object : FirebaseRecyclerAdapter<Mydata, ChatHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
                    // Create a new instance of the ViewHolder, in this case we are using a custom
                    // layout called R.layout.message for each item
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_layout, parent, false)


                    return ChatHolder(view)
                }

                override fun onBindViewHolder(holder: ChatHolder, position: Int, model: Mydata) {


                    holder.name.setText(model.name)

                    holder.itemView.setOnClickListener {

                        val ref = FirebaseDatabase.getInstance().reference
                        val applesQuery =
                            ref.child("Realtimedataabse").orderByChild("id").equalTo(model.id)

                        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (appleSnapshot in dataSnapshot.children) {

                                    var n = Mydata(model.id,"new data","new data","https://console.firebase.google.com/u/1/project/newproject-1b9d2/database/newproject-1b9d2-default-rtdb/data")
                                    appleSnapshot.ref.setValue(n)
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.e("======", "onCancelled", databaseError.toException())
                            }
                        })
                    }





                }


            }
        rec_data.adapter = adapter

    }

    override fun onStart() {
        super.onStart()

        adapter.startListening();

    }

    override fun onStop() {
        super.onStop()

        adapter.stopListening();

    }
}

class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    lateinit var name: TextView

    init {
        name = itemView.findViewById(R.id.name)
    }
}
