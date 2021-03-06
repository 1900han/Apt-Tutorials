package com.megamind.apttutorials

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.megamind.apttutorials.annotations.Builder
import com.megamind.apttutorials.annotations.Optional
import com.megamind.apttutorials.annotations.Required
import kotlinx.android.synthetic.main.activity_repository.*

@Builder
class RepositoryActivity: AppCompatActivity() {

    @Required
    lateinit var name: String

    @Required
    lateinit var owner: String

    @Optional(stringValue = "")
    lateinit var url: String

    @Optional(intValue = 123)
    var createAt: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        nameView.text = name
        ownerView.text = owner
        urlView.text = url
        createAtView.text = createAt.toString()
    }
}