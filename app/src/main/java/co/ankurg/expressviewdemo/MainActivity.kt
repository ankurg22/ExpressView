package co.ankurg.expressviewdemo

import android.os.Bundle
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import co.ankurg.expressview.ExpressView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val heartView = findViewById<ExpressView>(R.id.heartView)

        val likeView = findViewById<ExpressView>(R.id.likeView)
        likeView.interpolator = AnticipateOvershootInterpolator()
        likeView.iconAnimationDuration = 300

        val bulbView = findViewById<ExpressView>(R.id.bulbView)
        bulbView.interpolator = OvershootInterpolator()

        val fireView = findViewById<ExpressView>(R.id.fireView)
        fireView.interpolator = DecelerateInterpolator()
    }
}
