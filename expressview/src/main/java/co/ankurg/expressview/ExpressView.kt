package co.ankurg.expressview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import kotlin.math.min

/**
 * Created by Ankur Gupta on 31-01-2021.
 */

class ExpressView : FrameLayout, View.OnClickListener {
    private lateinit var root: View
    private lateinit var burstView: BurstView
    private lateinit var iconImageView: ImageView

    private var iconSize: Float = -1f

    private var uncheckedIcon = ContextCompat.getDrawable(
        context,
        R.drawable.expressview_ic_heart
    )
    private var checkedIcon = ContextCompat.getDrawable(
        context,
        R.drawable.expressview_ic_heart
    )
    private var uncheckedIconTint: Int = ContextCompat.getColor(
        context,
        R.color.default_unchecked_color
    )
    private var checkedIconTint = ContextCompat.getColor(
        context,
        R.color.default_checked_color
    )

    var animationStartDelay = 50L
    var iconAnimationDuration = 700L
    var burstAnimationDuration = 500L
    var interpolator: Interpolator = BounceInterpolator()

    private var onCheckListener: OnCheckListener? = null

    var isChecked: Boolean = false
        set(value) {
            field = value
            if (isChecked) {
                setIcon(iconImageView, checkedIcon)
                applyIconTint(iconImageView, checkedIconTint)
            } else {
                setIcon(iconImageView, uncheckedIcon)
                applyIconTint(iconImageView, uncheckedIconTint)
            }
            invalidate()
        }


    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        root = LayoutInflater.from(context)
            .inflate(R.layout.view_express, this, true)
        burstView = root.findViewById(R.id.burstView)
        iconImageView = root.findViewById(R.id.iconImageView)
        setupAttributes(attrs)
        setOnClickListener(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = min(measuredWidth, measuredHeight)

        val burstParams = burstView.layoutParams as LayoutParams
        burstParams.height = size
        burstParams.width = size

        val iconParams = iconImageView.layoutParams as LayoutParams
        iconParams.height = if (iconSize == -1f) (size / 1.8).toInt() else iconSize.toInt()
        iconParams.width = if (iconSize == -1f) (size / 1.8).toInt() else iconSize.toInt()

        setMeasuredDimension(size, size)
    }

    override fun onClick(v: View?) {
        isChecked = !isChecked

        if (!isChecked) {
            onCheckListener?.onUnChecked(this)
        } else {
            val animatorSet = AnimatorSet()
            val lineAnimator: ObjectAnimator =
                ObjectAnimator.ofFloat(burstView, BurstView.LINE_PROGRESS, 0f, 1f)
            lineAnimator.duration = burstAnimationDuration
            lineAnimator.startDelay = animationStartDelay

            val iconXAnimator: ObjectAnimator =
                ObjectAnimator.ofFloat(iconImageView, ImageView.SCALE_X, 0.2f, 1f)
            iconXAnimator.duration = iconAnimationDuration
            iconXAnimator.startDelay = animationStartDelay
            iconXAnimator.interpolator = interpolator

            val iconYAnimator: ObjectAnimator =
                ObjectAnimator.ofFloat(iconImageView, ImageView.SCALE_Y, 0.2f, 1f)
            iconYAnimator.duration = iconAnimationDuration
            iconYAnimator.startDelay = animationStartDelay
            iconYAnimator.interpolator = interpolator

            animatorSet.playTogether(lineAnimator, iconXAnimator, iconYAnimator)
            animatorSet.start()
            onCheckListener?.onChecked(this)
        }
    }

    fun setOnCheckListener(onCheckListener: OnCheckListener) {
        this.onCheckListener = onCheckListener
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.apply {
            val typedArray = context.theme.obtainStyledAttributes(
                this,
                R.styleable.ExpressView,
                0,
                0
            )

            iconSize = typedArray.getDimension(R.styleable.ExpressView_iconSize, iconSize)

            val drawable = typedArray.getDrawable(R.styleable.ExpressView_uncheckedIcon)
            if (drawable != null) uncheckedIcon = drawable
            iconImageView.setImageDrawable(uncheckedIcon)

            checkedIcon = typedArray.getDrawable(R.styleable.ExpressView_checkedIcon)
            if (checkedIcon == null) checkedIcon = uncheckedIcon

            uncheckedIconTint = typedArray.getColor(
                R.styleable.ExpressView_uncheckedIconTint,
                uncheckedIconTint
            )

            checkedIconTint = typedArray.getColor(
                R.styleable.ExpressView_checkedIconTint,
                checkedIconTint
            )

            burstView.burstColor = typedArray.getColor(
                R.styleable.ExpressView_burstColor,
                burstView.burstColor
            )

            isChecked = typedArray.getBoolean(R.styleable.ExpressView_setChecked, false)

            typedArray.recycle()
        }
    }

    private fun setIcon(iconImageView: ImageView, iconDrawable: Drawable?) {
        iconImageView.setImageDrawable(iconDrawable)
    }

    private fun applyIconTint(iconImageView: ImageView, tint: Int) {
        ImageViewCompat.setImageTintList(iconImageView, ColorStateList.valueOf(tint))
    }
}
