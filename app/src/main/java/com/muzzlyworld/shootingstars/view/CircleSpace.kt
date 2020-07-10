package com.muzzlyworld.shootingstars.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.muzzlyworld.shootingstars.nextAngle
import com.muzzlyworld.shootingstars.nextColor
import kotlin.random.Random

private const val MIN_OBJECT_SPEED = 1
private const val MIN_OBJECT_SIZE = 10

class CircleSpace(
    private val w: Int,
    private val h: Int,

    private val circleCount: Int
) : CircleMediator(){

    private val paint = Paint().apply {
        isAntiAlias = true
        isDither = true
    }

    private val movingCircles: MovingCircles = mutableListOf<MovingCircle>()
        .apply {
            repeat(circleCount){ add(generateNestedAdmissibleInstance(this)) }
        }

    fun update() = movingCircles.onEach { it.update() }

    fun draw(canvas: Canvas) = movingCircles.onEach {
        paint.color = it.color
        canvas.drawCircle(it.point.x, it.point.y, it.radius, paint)
    }

    override fun collisionCheck(circle: Circle): Boolean =
        circle.isContactTo(0, 0, w, h ) ||
                movingCircles.filter { circle != it }.any { it.isContactTo(circle)}


    private fun generateNestedAdmissibleInstance(rest: MovingCircles): MovingCircle{
        val radius = generateObjectSize()
        return MovingCircle(
            color = Random.nextColor(),
            direction = Random.nextAngle(),
            radius = radius,
            point = generateUnoccupiedPoint(radius, rest),
            speed = generateObjectSpeed(),
            mediator = this
        )
    }

    private fun generateUnoccupiedPoint(r: Float, rest: MovingCircles): PointF {
        val radius = r.toInt()

        fun randomCircle() = Circle(
            PointF(
                Random.nextInt(radius, w - radius).toFloat(),
                Random.nextInt(radius, h - radius).toFloat()
            ), r, this
        )

        var circle = randomCircle()

        while(rest.any { it.isContactTo(circle) }){
            circle = randomCircle()
        }

        return circle.point
    }

    private fun generateObjectSize(): Float{
        var maxSize = (w / 3 / (circleCount * 0.3)).toInt()
        if(maxSize <= MIN_OBJECT_SIZE) maxSize = MIN_OBJECT_SIZE + 1
        return Random.nextInt(MIN_OBJECT_SIZE, maxSize).toFloat()
    }

    private fun generateObjectSpeed(): Int{
        var maxSpeed = w / 50
        if(maxSpeed <= MIN_OBJECT_SPEED) maxSpeed = MIN_OBJECT_SPEED + 1
        return Random.nextInt(MIN_OBJECT_SPEED, (maxSpeed))
    }

}