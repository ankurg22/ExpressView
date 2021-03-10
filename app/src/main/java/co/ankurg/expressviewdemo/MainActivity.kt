package co.ankurg.expressviewdemo

import android.os.Bundle
import android.util.Log
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import co.ankurg.expressview.ExpressView
import co.ankurg.expressview.OnCheckListener

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
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
        fireView.animationStartDelay = 100
        fireView.setOnCheckListener(object : OnCheckListener {
            override fun onChecked(view: ExpressView?) {
                Log.d(TAG, "Checked")
            }

            override fun onUnChecked(view: ExpressView?) {
                Log.d(TAG, "Unchecked")
            }
        })
    }
}
