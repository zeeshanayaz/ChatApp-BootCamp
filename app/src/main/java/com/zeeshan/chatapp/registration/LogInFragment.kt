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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.dashboard.DashboardActivity
import com.zeeshan.chatapp.model.User
import com.zeeshan.chatapp.utils.AppPref
import kotlinx.android.synthetic.main.fragment_log_in.*

/**
 * A simple [Fragment] subclass.
 *
 */
class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    val mDatabase = FirebaseDatabase.getInstance()
    private lateinit var progress: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance();
        progress = ProgressDialog(activity)

        if (auth.currentUser != null) {
            startActivity(Intent(activity, DashboardActivity::class.java))
        }

        loginCreateUserBtn.setOnClickListener{
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LogOutFragment())
                .addToBackStack(null)
                .commit()

//            Snackbar.make(view,"Login Button Clicked",Snackbar.LENGTH_SHORT).setAction("Action",null).show()
        }

        loginLoginButton.setOnClickListener {
            if (!textEmailAddress.text.trim().toString().isNullOrEmpty()){
                if (!textPassword.text.trim().toString().isNullOrEmpty()){
                    Snackbar.make(view,"Trying to connect Server, Please Wait...", Snackbar.LENGTH_SHORT).setAction("Action",null).show()
                    authenticateUser(textEmailAddress.text.toString(), textPassword.text.toString())
                }
                else{
                    textPassword.setError("Invalid Password")
                }
            }
            else{
                textEmailAddress.setError("Invalid Email")
            }
        }


    }

    private fun authenticateUser(email: String, password: String) {

            progress.show()
            progress.setTitle("Signing to App.")
            progress.setMessage("Please wait, Signing to the App..")
            progress.setCancelable(false)

            auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {

                    mDatabase.reference.child("Users").child(it.user.uid)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()){
                                    val user = dataSnapshot.getValue(User::class.java)
                                    if (user != null){
                                        AppPref(activity!!).setUser(user)
                                        progress.dismiss()
                                        startActivity(Intent(activity, DashboardActivity::class.java))
                                    }
                                }
                                else{
                                    progress.dismiss()
                                    Toast.makeText(activity, "Error in signin ${it.toString()}", Toast.LENGTH_LONG).show()
                                }
                            }

                        })

                }
                .addOnFailureListener {
                    progress.dismiss()
                    it.printStackTrace()
                    Toast.makeText(activity, "Error in signin ${it.toString()}", Toast.LENGTH_LONG).show()
                }

    }

}
