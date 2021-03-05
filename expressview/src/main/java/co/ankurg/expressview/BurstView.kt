package co.ankurg.expressview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Property
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by Ankur Gupta on 22-01-2021.
 */

internal class BurstView : View {
    var burstColor: Int = ContextCompat.getColor(context, R.color.default_burst_color)
        set(value) {
            field = value
            paint.color = burstColor
        }

    private var lineProgress: Float = 0f
        set(value) {
            field = value
            updateLineLength()
            postInvalidate()
        }

    private var maxLineLength: Float = 0f
    private var lineLength: Float = 0f
    private var depletingLineLength: Float = 0f

    private var centerX: Int = 0
    private var centerY: Int = 0

    private lateinit var paint: Paint

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = burstColor
        paint.strokeWidth = 4f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
        maxLineLength = w / 2f
    }

    /**
     * https://stackoverflow.com/a/18656345
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until LINE_COUNT) {
            val startX = (centerX.toFloat() + depletingLineLength *
                    cos(Math.toRadians((i * LINE_ANGLE).toDouble()))).toFloat()
            val startY = (centerY.toFloat() + depletingLineLength *
                    sin(Math.toRadians((i * LINE_ANGLE).toDouble()))).toFloat()
            val stopX = (centerX + lineLength *
                    cos(Math.toRadians((i * LINE_ANGLE).toDouble()))).toFloat()
            val stopY = (centerY + lineLength *
                    sin(Math.toRadians((i * LINE_ANGLE).toDouble()))).toFloat()

            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    private fun updateLineLength() {
        if (lineProgress < 0.3f) {
            this.lineLength = mapValueFromRangeToRange(
                value = lineProgress,
                fromLow = 0.0f,
                fromHigh = 0.7f,
                toLow = 0f,
                toHigh = maxLineLength * 0.8f
            )
            this.depletingLineLength = mapValueFromRangeToRange(
                value = lineProgress,
                fromLow = 0.0f,
                fromHigh = 0.3f,
                toLow = 0f,
                toHigh = maxLineLength * 0.8f
            )
        } else {
            this.lineLength = mapValueFromRangeToRange(
                value = lineProgress,
                fromLow = 0.7f,
                fromHigh = 1f,
                toLow = 0.8f * maxLineLength,
                toHigh = maxLineLength
            )
            this.depletingLineLength = mapValueFromRangeToRange(
                value = lineProgress,
                fromLow = 0.3f,
                fromHigh = 1f,
                toLow = 0.8f * maxLineLength,
                toHigh = maxLineLength
            )
        }
    }

    /**
     * https://stackoverflow.com/a/345204
     */
    private fun mapValueFromRangeToRange(
        value: Float,
        fromLow: Float,
        fromHigh: Float,
        toLow: Float,
        toHigh: Float
    ): Float {
        return (value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow) + toLow
    }

    companion object {
        private const val LINE_COUNT = 8
        private const val LINE_ANGLE = 360 / LINE_COUNT

        val LINE_PROGRESS: Property<BurstView, Float> =
            object : Property<BurstView, Float>(Float::class.java, "lineProgress") {
                override operator fun get(view: BurstView): Float {
                    return view.lineProgress
                }

                override operator fun set(view: BurstView, value: Float) {
                    view.lineProgress = value
                }
            }
    }
}
