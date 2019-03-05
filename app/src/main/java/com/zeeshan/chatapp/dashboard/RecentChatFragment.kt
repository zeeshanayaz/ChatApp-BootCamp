package com.zeeshan.chatapp.dashboard


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.adapter.UserListAdapter
import com.zeeshan.chatapp.model.ChatMessage
import com.zeeshan.chatapp.model.User

/**
 * A simple [Fragment] subclass.
 *
 */
class RecentChatFragment : Fragment() {

    private var recentUserList = ArrayList<User>()
    private lateinit var recentChatViewAdapter: UserListAdapter
    private lateinit var databaseRef: DatabaseReference
    val curUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recent_chat, container, false)

//        databaseRef = FirebaseDatabase.getInstance().reference
//
//        val recyclerView = view.findViewById<RecyclerView>(R.id.dashboardRecentChatListRecycler)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//
//        recentChatViewAdapter = UserListAdapter(activity!!, recentUserList) {
//            val chatIntent = Intent(activity!!, ChatActivity::class.java)
//            chatIntent.putExtra("user", it)
//            startActivity(chatIntent)
////            Toast.makeText(activity!!, "Clicked ${it.userEmail}", Toast.LENGTH_SHORT).show()
//        }
//
//        recyclerView.adapter = recentChatViewAdapter
//
//        fetchRecentChatsFromFirebase()
//        fetFCM()


        return view
    }

//    private fun fetchRecentChatsFromFirebase() {
//        databaseRef.child("user-chat").addChildEventListener(object : ChildEventListener {
//            override fun onCancelled(dataSnapshot: DatabaseError) {
//
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
////                if (dataSnapshot != null) {
//                    val recentChats = dataSnapshot.getValue((ChatMessage::class.java))
//                    if (recentChats != null) {
//                        val chatID = recentChats.chatId
//                        val list = chatID.split("-")
////                        if(list[0].equals(curUserId) || list[1].equals(curUserId)){
////
////                        }
//
//                        when (curUserId) {
//                            list[0] -> {
//                                databaseRef.child("Users").child(list[1])
//                                    .addValueEventListener(object : ValueEventListener {
//                                        override fun onCancelled(p0: DatabaseError) {}
//
//                                        override fun onDataChange(userDataSnapshot: DataSnapshot) {
//                                            val user = userDataSnapshot.getValue(User::class.java)
//
//                                            Log.d("ChatLIST", user.toString())
//                                            if (user != null) {
//                                                recentUserList.add(user)
//
//                                                recentChatViewAdapter.notifyDataSetChanged()
//                                            }
//
//                                        }
//
//                                    })
//                            }
//                            list[1] -> {
//                                databaseRef.child("Users").child(list[2])
//                                    .addValueEventListener(object : ValueEventListener {
//                                        override fun onCancelled(p0: DatabaseError) {}
//
//                                        override fun onDataChange(userDataSnapshot: DataSnapshot) {
//                                            val user = userDataSnapshot.getValue(User::class.java)
//
//                                            Log.d("ChatLIST", user.toString())
//                                            if (user != null) {
//                                                recentUserList.add(user)
//
//                                                recentChatViewAdapter.notifyDataSetChanged()
//                                            }
//
//                                        }
//
//                                    })
//
//                            }
//                            else -> {
//                                println("Not Matched")
//                            }
//
//                        }
////                        if (list[0].equals(curUserId)) {
////                            databaseRef.child("Users").child(list[1])
////                                .addValueEventListener(object : ValueEventListener {
////                                    override fun onCancelled(p0: DatabaseError) {}
////
////                                    override fun onDataChange(userDataSnapshot: DataSnapshot) {
////                                        val user = userDataSnapshot.getValue(User::class.java)
////
////                                        Log.d("ChatLIST", user.toString())
////                                        if (user != null) {
////                                            recentUserList.add(user)
////
////                                            recentChatViewAdapter.notifyDataSetChanged()
////                                        }
////
////                                    }
////
////                                })
////
////                        } else if (list[1].equals(curUserId)) {
////                            databaseRef.child("Users").child(list[2])
////                                .addValueEventListener(object : ValueEventListener {
////                                    override fun onCancelled(p0: DatabaseError) {}
////
////                                    override fun onDataChange(userDataSnapshot: DataSnapshot) {
////                                        val user = userDataSnapshot.getValue(User::class.java)
////
////                                        Log.d("ChatLIST", user.toString())
////                                        if (user != null) {
////                                            recentUserList.add(user)
////
////                                            recentChatViewAdapter.notifyDataSetChanged()
////                                        }
////
////                                    }
////
////                                })
////                        }
//
//                    }
//
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//
//            }
//
//        })
//    }

//    private fun fetFCM() {
//
//        val token = FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(object :
//            OnCompleteListener<InstanceIdResult> {
//            override fun onComplete(task: Task<InstanceIdResult>) {
//                if (!task.isSuccessful) {
//                    Toast.makeText(activity!!, "unable to get Fcm", Toast.LENGTH_SHORT).show()
//                    return
//                }
//                val token = task.result!!.token
//                Log.d("FCM", token)
//                sendTokenToServer(token)
//            }
//        })
//    }


//    private fun sendTokenToServer(token: String) {
//        FirebaseDatabase.getInstance().getReference()
//            .child("Users")
//            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
//            .child("fcm").setValue(token)
//    }

}
