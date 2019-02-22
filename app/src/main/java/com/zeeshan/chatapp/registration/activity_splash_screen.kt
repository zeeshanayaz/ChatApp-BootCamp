package com.zeeshan.chatapp.registration

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zeeshan.chatapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.lang.Exception

class activity_splash_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val background = object :Thread(){
            override fun run() {
                try {

                    for (progress in 1..5)
                    {
                        Thread.sleep(1000)
                        progressBar.setProgress(progress);
                    }

                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
