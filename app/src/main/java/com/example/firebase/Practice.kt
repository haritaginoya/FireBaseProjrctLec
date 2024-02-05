//package com.example.firebase
//
//import android.R
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.RecyclerView
//import com.firebase.ui.database.FirebaseRecyclerAdapter
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.Query
//
//
//class Practice : AppCompatActivity() {
//    lateinit var recycle : RecyclerView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_practice)
//        recycle = findViewById(R.id.recycle)
//
//
//        val query: Query = FirebaseDatabase.getInstance()
//            .reference
//            .child("chats")
//            .limitToLast(50)
//
//        val adapter: FirebaseRecyclerAdapter<*, *> =
//            object : FirebaseRecyclerAdapter<Mydata, Mydatholder>(options) {
//                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mydatholder {
//                    // Create a new instance of the ViewHolder, in this case we are using a custom
//                    // layout called R.layout.message for each item
//
//                    val view: View = LayoutInflater.from(parent.context)
//                        .inflate(R.layout.message, parent, false)
//                    return ChatHolder(view)
//                }
//
//                protected override fun onBindViewHolder(
//                    holder: ChatHolder?,
//                    position: Int,
//                    model: Chat?
//                ) {
//                    // Bind the Chat object to the ChatHolder
//                    // ...
//                }
//            }
//
//
//
//
//    }
//
//    class Mydatholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }
//}