package com.zeeshan.chatapp.dashboard


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

import com.zeeshan.chatapp.R
import com.zeeshan.chatapp.model.User
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    val user = FirebaseAuth.getInstance().currentUser
    private lateinit var databaseRef : DatabaseReference
    var selectedPhotoUri : Uri? = null
    private lateinit var progress : ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = ProgressDialog(activity)
        databaseRef = FirebaseDatabase.getInstance().reference

        val userRef = databaseRef.child("Users").child(user!!.uid)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.getValue(User::class.java)

                if (!userData?.userName.isNullOrEmpty())
                {
                    profileUserNameText!!.text = userData?.userName.toString()
                }
                else
                {
                    profileUserNameText.text = "user name"
                    profileUserNameText.setTextColor(resources.getColor(R.color.colorSecondaryText))
                }
                cardUserName.text = userData?.userEmail.toString()

                if (!userData?.userBio.isNullOrEmpty())
                {
                    profileBioTxt.text = userData?.userBio.toString()
                }
                else
                {
                    profileBioTxt.text = "No Bio Added  `"
                        profileBioTxt.setTextColor(resources.getColor(R.color.colorSecondaryText))
                }
            }

        })

//Select Photo Button

        profileSeletPhotoBtn.setOnClickListener {
            Log.d("ProfileFragment", "Profile Select Photo Button Pressed")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
//            Snackbar.make(view, "Select Photo", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }

        profileUpdateBtn.setOnClickListener {
            Log.d("ProfileFragment", "Update Button Pressed")

            progress.show()
            progress.setTitle("Updating...")
            progress.setMessage("Updating...")
            progress.setCancelable(false)

            if (selectedPhotoUri != null)
            {
                Log.d("ProfileFragment", "Uploading Image to Firebase Storage")
                uploadImageToFirebase()
                Log.d("ProfileFragment", "Progress Bar Dismissed")
                progress.dismiss()
                Snackbar.make(view, "Profile Updated", Snackbar.LENGTH_SHORT).setAction("Action", null).show()

                return@setOnClickListener
            }
            progress.dismiss()
            Snackbar.make(view, "Nothing to Update!", Snackbar.LENGTH_SHORT).setAction("Action", null).show()

        }


//        userNameEditBtn.setOnClickListener {
//            val editInputDialog = LayoutInflater.from(activity).inflate(R.layout.profile_edit_dialog, null)
//            val dialogBuilder = AlertDialog.Builder(activity!!)
//                .setView(editInputDialog)
//                .setTitle("Insert New User Name")
//                .show()
//
//            editInputDialog.btn_edit_profile.setOnClickListener {
//                profileUserNameText.setText(editInputDialog.input_edit_profile.text.toString())
//                dialogBuilder.dismiss()
//
////                Snackbar.make(
////                    it,
////                    "Profile Updated with User Name ${input_edit_profile.text.toString()}",
////                    Snackbar.LENGTH_SHORT
////                ).setAction("Action", null).show()
//            }
//        }



    }

    private fun uploadImageToFirebase() {
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/profileImages/$fileName")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("ProfileFragment", "Successfully Uploaded Image : ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
//                    it.toString()
                    databaseRef.child("Users").child("${user?.uid}").child("profileImageUrl").setValue(it.toString())
                    Log.d("ProfileFragment", "Successfully Saved Image URL : $it")
                }
            }
            .addOnFailureListener {

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
        {
            Log.d("ProfileFragment", "Photo was selected")

            selectedPhotoUri = data.data
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val inputStream = activity!!.contentResolver.openInputStream(selectedPhotoUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            profileImageImageView.setImageBitmap(bitmap)
            profileSeletPhotoBtn.alpha = 0f
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            profileSeletPhotoBtn.setBackgroundDrawable(bitmapDrawable)

        }
    }
}