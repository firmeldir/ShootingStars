package com.muzzlyworld.shootingstars.view

import android.graphics.PointF
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import com.muzzlyworld.shootingstars.nextAngle
import com.muzzlyworld.shootingstars.nextColor
import com.muzzlyworld.shootingstars.toRadian
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

typealias MovingCircles = List<MovingCircle>

private const val MIN_SPEED = 1
private const val MIN_SIZE = 10

class MovingCircle(
    radius: Float,
    point: PointF,

    @ColorInt val color: Int,
    @IntRange(from = 0) val speed: Int,
    @IntRange(from = 0, to = 359) var direction: Int,

    private val space: Space
): Circle(radius, point){

    class Space(
        val width: Int,
        val height: Int,

        val movingCircles: List<MovingCircle>
    )

    fun update(){

        point.x += speed * cos(direction.toRadian())
        point.y += speed * sin(direction.toRadian())

        if( this.isContactTo(0, 0, space.width, space.height)
            || space.movingCircles.filter { it != this }.any { it.isContactTo(this) }
        ) oppositeDirection()

    }

    private fun oppositeDirection(){
        direction = (direction + 180) % 360
    }

    companion object{

        fun nestedRandomInstance(space: Space, circleCount: Int): MovingCircle{
            val radius = randomSize(space, circleCount)

            return MovingCircle(
                color = Random.nextColor(),
                direction = Random.nextAngle(),

                radius = radius,
                point = unoccupiedPoint(radius, space),
                speed = randomSpeed(space),

                space = space
            )
        }


        private fun unoccupiedPoint(r: Float, space: Space): PointF{
            val radius = r.toInt()

            fun randomCircle() = Circle(
                r, PointF(
                    Random.nextInt(radius, space.width - radius).toFloat(),
                    Random.nextInt(radius, space.height - radius).toFloat()
                )
            )

            var circle = randomCircle()

            while(space.movingCircles.any { it.isContactTo(circle) }){
                circle = randomCircle()
            }

            return circle.point
        }


        private fun randomSize(space: Space, circleCount: Int): Float{
            var maxSize = (space.width / 3 / (circleCount * 0.3)).toInt()
            if(maxSize <= MIN_SIZE) maxSize = MIN_SIZE + 1
            return Random.nextInt(MIN_SIZE, maxSize).toFloat()
        }

        private fun randomSpeed(space: Space) = Random.nextInt(MIN_SPEED, (space.width / 50))
    }
}