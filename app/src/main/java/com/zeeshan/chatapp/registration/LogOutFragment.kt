package com.zeeshan.chatapp.registration


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.dashboard.DashboardActivity
import com.zeeshan.chatapp.model.User
import kotlinx.android.synthetic.main.fragment_log_out.*

/**
 * A simple [Fragment] subclass.
 *
 */
class LogOutFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var progress : ProgressDialog
    private lateinit var databaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance();
        progress = ProgressDialog(activity)
        databaseRef = FirebaseDatabase.getInstance().reference

        createUserBtn.setOnClickListener {
            progress.show()
            registerUser( createTextEmailAddress.text.trim().toString(), createTextPassword.text.toString())
//            Snackbar.make(view,"Create User Button Clicked", Snackbar.LENGTH_SHORT).setAction("Action",null).show()
        }

        createLoginBtn.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }



    private fun registerUser(email: String, password: String) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty() )
        {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){

                    val authResult = it.result
                    authResult?.user?.uid
                    saveUserDataToFirebase(authResult?.user?.uid,"", email, password, "")
                    progress.dismiss()
                    Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    startActivity(Intent(activity, DashboardActivity::class.java))

//
                }
                else{
                    progress.dismiss()
                    Toast.makeText(activity, "User not created ${it.exception.toString()}", Toast.LENGTH_LONG).show()
                }
            }
        }
        else
        {
            createTextEmailAddress.setError("Error")
            createTextPassword.setError("Error")
        }
    }

    private fun saveUserDataToFirebase(
        uid: String?,
        userName: String,
        email: String,
        password: String,
        userBio: String
    ) {view
        val user = User("$uid", "", "$email", "$password", "")
        databaseRef.child("Users").child("$uid").setValue(user)

        Snackbar.make(view!!, "User Created in Databasse", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

}
