package com.muzzlyworld.shootingstars.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.provider.SyncStateContract.Helpers.update
import android.util.AttributeSet
import android.view.View

const val DEFAULT_CIRCLE_COUNT = 15
const val MAX_CIRCLE_COUNT = 200

class ShootingStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var circleCount = DEFAULT_CIRCLE_COUNT
        set(value) {
            field = if(value > MAX_CIRCLE_COUNT) MAX_CIRCLE_COUNT else value
        }

    private lateinit var circleSpace: CircleSpace

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleSpace = CircleSpace(w, h, circleCount)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circleSpace.update()
        circleSpace.draw(canvas!!)
        invalidate()
    }
}