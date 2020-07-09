package com.muzzlyworld.shootingstars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muzzlyworld.shootingstars.view.ShootingStarsView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ShootingStarsView(this).apply { circleCount = 15 }
        )
    }
}