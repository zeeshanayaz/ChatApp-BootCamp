package com.zeeshan.chatapp.dashboard


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import com.zeeshan.chatapp.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_edit_dialog.*
import kotlinx.android.synthetic.main.profile_edit_dialog.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        userNameProfileText.text = user?.uid

        userNameEditBtn.setOnClickListener {
            val editInputDialog = LayoutInflater.from(activity).inflate(R.layout.profile_edit_dialog, null)
            val dialogBuilder = AlertDialog.Builder(activity!!)
                .setView(editInputDialog)
//                .setTitle("Add Todos")
                .show()

            editInputDialog.btn_edit_profile.setOnClickListener {
                userNameProfileText.setText(editInputDialog.input_edit_profile.text.toString())
                dialogBuilder.dismiss()

//                Snackbar.make(
//                    it,
//                    "Profile Updated with User Name ${input_edit_profile.text.toString()}",
//                    Snackbar.LENGTH_SHORT
//                ).setAction("Action", null).show()
            }
        }

//        userProfileImage.setOnClickListener {
//            var intent = Intent(Intent.ACTION_PICK)
//            intent.setType("*/*")
//            startActivityForResult(intent, 911)
//        }
//

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == Activity.RESULT_OK && requestCode == 911){
//            val uri = data?.data
//            val inputStream = this@ProfileFragment.getContentResolver().openInputStream(uri)
//        }
//    }

    }
}