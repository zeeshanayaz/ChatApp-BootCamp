package com.zeeshan.chatapp.dashboard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.adapter.UserChatListAdapter
import com.zeeshan.chatapp.model.ChatMessage
import com.zeeshan.chatapp.model.User
import com.zeeshan.chatapp.utils.AppPref
import kotlinx.android.synthetic.main.activity_chat.*
import java.sql.Timestamp
import java.util.*
import kotlin.math.log

class ChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter : UserChatListAdapter
    var userChatList: ArrayList<ChatMessage> = ArrayList()
    lateinit var user: User


    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        if (intent != null && intent.extras != null){
            val extras = intent.extras
            user = extras.getSerializable("user") as User

        }
        else{
            finish()
        }

//        if (user!= null){
//            Toast.makeText(this@ChatActivity, "${user.userName}", Toast.LENGTH_SHORT).show()
//        }

        chatMessageListRecycler.layoutManager = LinearLayoutManager(this@ChatActivity)
        chatAdapter = UserChatListAdapter(this@ChatActivity, userChatList)
        chatMessageListRecycler.adapter = chatAdapter

//        Log.v("Chat","${userChatList.size - 1}")
//        chatMessageListRecycler.scrollToPosition(userChatList.size - 1)

        recieveMessages()

//        (chatMessageListRecycler.layoutManager as LinearLayoutManager).scrollToPosition(userChatList.lastIndex)

        sendMessageButton.setOnClickListener {
            if (!messageTextField.text.trim().isNullOrEmpty()){
                val currUser = AppPref(this@ChatActivity).getUser()

                var chatID = user.userID + "-" + currUser?.userID
                val list = chatID.split("-")
                val sorted = list.sorted()
                chatID = sorted[0] + "-" + sorted[1]

                val msgID = System.currentTimeMillis().toString()

                val chatMessage = ChatMessage(
                    chatID,
                    messageTextField.text.toString().trim(),
                    System.currentTimeMillis(),
                    msgID,
                    currUser?.userID!!
                )

                FirebaseDatabase.getInstance().getReference().child("user-chat").child(chatID).child(msgID)
                    .setValue(chatMessage)
                messageTextField.setText("")
            }
        }



    }

    private fun recieveMessages() {
        val currUser = AppPref(this@ChatActivity).getUser()

        var chatID = user.userID + "-" + currUser?.userID
        val list = chatID.split("-")
        val sorted = list.sorted()
        chatID = sorted[0] + "-" + sorted[1]

        FirebaseDatabase.getInstance().getReference().child("user-chat").child(chatID)
            .addChildEventListener(object : ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    if (dataSnapshot.exists()) {
                        val value = dataSnapshot.getValue(ChatMessage::class.java)
                        if (value != null) {
                            userChatList.add(value)
                            chatMessageListRecycler.scrollToPosition(userChatList.size - 1)
                            chatAdapter.notifyDataSetChanged()

                        }
                    }
                }
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                }

            })
    }
}
