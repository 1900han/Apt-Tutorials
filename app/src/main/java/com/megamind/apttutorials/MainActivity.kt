package com.megamind.apttutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToRepositoryActivity.setOnClickListener {
            startRepositoryActivity("Kotlin", "JetBrains")
        }
        goToUserActivity.setOnClickListener {
            UserActivityBuilder.start(this, 30, "bennyhuo", "Kotliner", "打杂的", "北京")
        }
    }
}