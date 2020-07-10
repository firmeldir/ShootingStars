package com.muzzlyworld.shootingstars.view

import android.graphics.PointF
import com.muzzlyworld.shootingstars.dist

open class Circle(
    val point: PointF,
    val radius: Float,

    private val mediator: CircleMediator
){

    val isContacted: Boolean get() =  mediator.collisionCheck(this)


    /**     Frame rect    */

    fun isContactTo(x: Int, y: Int , width: Int, height: Int): Boolean =
        (point.x - radius < x) || (point.x + radius > x + width) || (point.y - radius < y) || (point.y + radius > y + height)


    fun isContactTo(circle: Circle): Boolean =
        point.dist(circle.point) - radius - circle.radius <= 0


    fun move(x: Float, y: Float){
        point.x += x
        point.y += y
    }
}