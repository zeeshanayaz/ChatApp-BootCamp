package com.zeeshan.chatapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.model.User


class UserListAdapter (
    var ctx : Context, var dataList : ArrayList<User>,
    var itemClick: (user: User) -> Unit
):
    RecyclerView.Adapter<UserListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(ctx).inflate(R.layout.user_layout, null, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
//        val data = dataList.get(position)
//        if(data != null){
//            viewHolder.userName.text = data.userName
//            viewHolder.email.text = data.userEmail
//
//        }
        viewHolder.bindUser(dataList[position])
    }


    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        val userName = view.findViewById<TextView>(R.id.cardUserName)
        val email = view.findViewById<TextView>(R.id.cardUserEmail)

        fun bindUser(user: User){
            userName.text = user.userName
            email.text = user.userEmail

            view.setOnClickListener{
                itemClick(user)
//                view.itemClick(user)

            }

        }

    }
}