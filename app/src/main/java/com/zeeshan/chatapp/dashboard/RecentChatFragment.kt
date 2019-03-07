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
import com.google.firebase.database.*
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.adapter.UserListAdapter
import com.zeeshan.chatapp.model.User
import com.zeeshan.chatapp.utils.AppPref

/**
 * A simple [Fragment] subclass.
 *
 */
class RecentChatFragment : Fragment() {


    private var recentUserList = ArrayList<User>()
    private var recentChatID = ArrayList<String>()
    private lateinit var recentChatViewAdapter: UserListAdapter
    private lateinit var databaseRef: DatabaseReference
//    val curUserId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recent_chat, container, false)

        databaseRef = FirebaseDatabase.getInstance().reference



        val recyclerView = view.findViewById<RecyclerView>(R.id.dashboardRecentChatListRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recentChatViewAdapter = UserListAdapter(activity!!, recentUserList) {
            val chatIntent = Intent(activity!!, ChatActivity::class.java)
            chatIntent.putExtra("user", it)
            startActivity(chatIntent)
//            Toast.makeText(activity!!, "Clicked ${it.userEmail}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = recentChatViewAdapter

//        Toast.makeText(activity, "View", Toast.LENGTH_SHORT).show()


        fetcingChatIDFromFirebase()

//        fetFCM()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fetchRecentChatsFromFirebase()
    }

    private fun fetcingChatIDFromFirebase() {
        val curUser = AppPref(activity!!).getUser()
        val key = databaseRef.child("user-chat").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val chatId = it.key.toString()
                    val list = chatId.split("-")
                    when (curUser?.userID) {
                        list[0] -> {
                            recentChatID.add(list[1])
                        }
                        list[1] -> {
                            recentChatID.add(list[1])
                        }
                    }
//                    Log.v("Key", it.key.toString())
                }
//                Toast.makeText(activity,"Recent Chat Id",Toast.LENGTH_SHORT).show()

                Log.v("KEYS", recentChatID.toString())
            }

        })

        fetchRecentChatsFromFirebase()


    }

    private fun fetchRecentChatsFromFirebase() {
        val curUser = AppPref(activity!!).getUser()
        databaseRef.child("Users").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
//                val user = dataSnapshot.getValue(User::class.java)
//                recentUserList.add(user!!)
//                recentChatViewAdapter.notifyDataSetChanged()
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                if (dataSnapshot != null) {
                    val user = dataSnapshot.getValue((User::class.java))
                    if (user != null) {
                        for (item in recentChatID) {
                            if (item == user.userID && item != curUser?.userID) {
                                println(user.toString())
                                recentUserList.add(user)
                                recentChatViewAdapter.notifyDataSetChanged()
                            }
                        }
//                        recentChatID.forEach {
//                            if (it.equals(user.userID))
//                            {
//                                if (!user.userID.equals(curUserId)) {
//                                    println(user.toString())
//                                    recentUserList.add(user)
//                                    recentChatViewAdapter.notifyDataSetChanged()
//
//                                }
//                            }
//                        }
                    }
                }
//                Toast.makeText(activity,"Recycler View Updated",Toast.LENGTH_SHORT).show()

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

        })
    }
    override fun onStop() {
        super.onStop()
        Log.v("RECENTCHAT", "User List Fragment on Stop ")
//        making Array List null
        recentChatID.clear()
        recentUserList.clear()

//        println(userList)
    }

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
