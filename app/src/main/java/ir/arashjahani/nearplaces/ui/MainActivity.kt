package ir.arashjahani.nearplaces.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.arashjahani.nearplaces.R
import ir.arashjahani.nearplaces.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
