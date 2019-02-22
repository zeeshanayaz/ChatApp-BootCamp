package com.zeeshan.chatapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.model.User
import kotlinx.android.synthetic.main.user_layout.view.*

class UserListAdapter (var ctx : Context, var dataList : ArrayList<User>): RecyclerView.Adapter<UserListAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(p0.context).inflate(R.layout.user_layout, null)
        return UserListAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, p1: Int) {
        val data = dataList.get(p1)
        if(data != null){
            viewHolder.userName.text = data.userName
            viewHolder.email.text = data.userEmail
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userName = view.cardUserName
        val email = view.cardUserEmail
    }
}