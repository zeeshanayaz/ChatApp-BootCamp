package com.zeeshan.chatapp.dashboard


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.adapter.UserListAdapter
import com.zeeshan.chatapp.model.User

class UserListFragment : Fragment() {

    private var userList = ArrayList<User>()
    private lateinit var userViewAdapter: UserListAdapter
    private lateinit var databaseRef: DatabaseReference
    val curUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        databaseRef = FirebaseDatabase.getInstance().reference


//        Recycler View
        val recyclerView = view.findViewById<RecyclerView>(R.id.dashboardAllUserListRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        userViewAdapter = UserListAdapter(activity!!, userList) {
            val chatIntent = Intent(activity!!, ChatActivity::class.java)
            chatIntent.putExtra("user", it)
            startActivity(chatIntent)
//            Toast.makeText(activity!!, "Clicked ${it.userEmail}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = userViewAdapter

        fetchDataFromFirebase()

    }

    private fun fetchDataFromFirebase() {
        databaseRef.child("Users").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                if (dataSnapshot != null) {
                    val user = dataSnapshot.getValue(User::class.java)
                    userList.forEachIndexed { index, userObj ->
                        if (userObj.equals(user)) {
                            println("Matched")
                            userList[index] = user!!
                            userViewAdapter.notifyDataSetChanged()
                        } else {
                            println("Not Matched")
                        }
                    }
                }
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                if (dataSnapshot != null) {
                    val user = dataSnapshot.getValue((User::class.java))
                    if (user != null) {
                        if (!user.userID.equals(curUser?.uid)) {
                            println(user.toString())
                            userList.add(user)
                            userViewAdapter.notifyDataSetChanged()

                        }
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    val user = dataSnapshot.getValue((User::class.java))
                    var position: Int = 0

                    userList.forEachIndexed { index, userObj ->
                        if (userObj.equals(user)) {
                            println("Matched")
                            position = index
                        } else {
                            println("Not Matched")
                        }
                        userList.removeAt(position)
                        userViewAdapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

}
