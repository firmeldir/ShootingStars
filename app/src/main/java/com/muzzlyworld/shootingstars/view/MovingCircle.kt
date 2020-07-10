package com.muzzlyworld.shootingstars.view

import android.graphics.PointF
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import com.muzzlyworld.shootingstars.toRadian
import kotlin.math.cos
import kotlin.math.sin

typealias MovingCircles = List<MovingCircle>

class MovingCircle(
    radius: Float,
    point: PointF,
    mediator: CircleMediator,

    @ColorInt val color: Int,
    @IntRange(from = 0) private val speed: Int,
    @IntRange(from = 0, to = 359) private var direction: Int
): Circle(point, radius, mediator){

    private val xs get() = speed * cos(direction.toRadian())
    private val ys get() = speed * sin(direction.toRadian())

    fun update() = move(xs, ys).also { if(isContacted) oppositeDirection() }

    private fun oppositeDirection(){
        direction = (direction + 180) % 360
    }
}