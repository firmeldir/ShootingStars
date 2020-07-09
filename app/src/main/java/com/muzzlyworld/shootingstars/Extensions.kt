package com.muzzlyworld.shootingstars

import android.graphics.Color
import android.graphics.PointF
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random


fun Random.nextColor() =
    Color.argb(
        255,
        this.nextInt(0, 256),
        this.nextInt(0, 256),
        this.nextInt(0, 256)
    )

fun Random.nextAngle() =
    this.nextInt(0, 360)

fun Int.toRadian(): Float = (this.toFloat() / 180 * Math.PI).toFloat()


fun PointF.dist(p: PointF): Float
        = sqrt((p.x - this.x.toDouble()).pow(2.0) + (p.y - this.y.toDouble()).pow(2.0)).toFloat()