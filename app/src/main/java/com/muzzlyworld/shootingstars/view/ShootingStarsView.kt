package com.muzzlyworld.shootingstars.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

const val MAX_CIRCLE_COUNT = 200
const val DEFAULT_CIRCLE_COUNT = 15

class ShootingStarsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var circleCount = DEFAULT_CIRCLE_COUNT
        set(value) {
            field = if(value > MAX_CIRCLE_COUNT) MAX_CIRCLE_COUNT else value
        }

    private var movingCircles: MovingCircles? = null

    private fun initCircles(w: Int, h: Int) {
        movingCircles = mutableListOf<MovingCircle>().apply {
            repeat(circleCount){ add(MovingCircle.nestedRandomInstance(MovingCircle.Space(w, h, this), circleCount)) }
        }
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        isDither = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initCircles(w, h)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        update()
        drawStars(canvas!!)
        invalidate()
    }

    private fun update(){ movingCircles?.onEach {
        it.update()
    } }

    private fun drawStars(canvas: Canvas){ movingCircles?.onEach {
        paint.color = it.color
        canvas.drawCircle(it.point.x, it.point.y, it.radius, paint)
    } }
}