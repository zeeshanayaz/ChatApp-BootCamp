package com.zeeshan.chatapp.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.model.User
import com.zeeshan.chatapp.registration.MainActivity
import com.zeeshan.chatapp.utils.AppPref

class DashboardActivity : AppCompatActivity() {

    private lateinit var databaseRef : DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.dashboardContainer, UserListFragment())
            .commit()

        val user = FirebaseAuth.getInstance().currentUser
        databaseRef = FirebaseDatabase.getInstance().reference



//        println("User "+user?.email)

        val dashboardBottomNavigation : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        dashboardBottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_all_user -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.dashboardContainer, UserListFragment())
                        .commit()
                    true
                }
                R.id.navigation_chats -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.dashboardContainer, RecentChatFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){

            R.id.menu_sign_out -> {
                showPopup()
                return true
            }

            R.id.menu_profile -> {
                val fragment = supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, ProfileFragment()).addToBackStack(null)
                        Toast.makeText(this, "Profile Setting Icon Clilcked", Toast.LENGTH_SHORT).show()
                        fragment.commit()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showPopup(){
        val dialogBuilder = AlertDialog.Builder(this@DashboardActivity)
        val create: AlertDialog = dialogBuilder.create()

        dialogBuilder.setCancelable(false)

        dialogBuilder.setTitle("Signing Out!")
        dialogBuilder.setMessage("Do you want to Sign out!")

        dialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                startActivity(intent)

                finish()

                val user = User("","","","","","")
                AppPref(this@DashboardActivity).setUser(user)
            }
        })

        dialogBuilder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                create.dismiss()
            }
        })
        dialogBuilder.create()
        dialogBuilder.show()
    }

}
