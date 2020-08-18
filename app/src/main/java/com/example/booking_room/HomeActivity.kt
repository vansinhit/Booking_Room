package com.example.booking_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booking_room.fragments.BookingFragment
import com.example.booking_room.fragments.HomeFragment
import com.example.booking_room.fragments.ManagementFragment
import com.example.booking_room.services.AuthService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        actionFliper()
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        val homeFragment = HomeFragment()
        val bookingFragment = BookingFragment()
        val managementFragment = ManagementFragment()

        bottomSheetDialog.setContentView(view)

        makeCurrentFragment(homeFragment);

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.booking -> makeCurrentFragment(bookingFragment)
                R.id.management -> makeCurrentFragment(managementFragment)
                R.id.account -> bottomSheetDialog.show()
            }
            true
        }

        view.logout.setOnClickListener {
            bottomSheetDialog.hide()
            Toast.makeText(baseContext, "Log Out", Toast.LENGTH_LONG).show()
            AuthService.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    private fun actionFliper(){
        var arrhinhanh: ArrayList<String> = ArrayList()
        arrhinhanh.add("https://ezcloud.vn/wp-content/uploads/2019/07/4649_abc-1.jpg")
       arrhinhanh.add("https://oxydesign.vn/public/media/media/images/sp-nhiep-anh/khach-san/Novotel%20Phu%20Quoc/Novotel%20Phu%20Quoc_12.jpg")
        arrhinhanh.add("https://cdn3.ivivu.com/2014/01/SUPER-DELUXE2.jpg")
        arrhinhanh.add("https://lavenderstudio.com.vn/wp-content/uploads/2017/08/chup-hinh-nha-hang-khach-san-3.png")
        for (i in arrhinhanh){
            var imageview = ImageView(applicationContext)
            Picasso.with(application)
                .load(i).into(imageview)
            imageview.scaleType = ImageView.ScaleType.FIT_XY
            vfliper.addView(imageview)

        }
        vfliper.flipInterval = 5000
        vfliper.isAutoStart = true
        var inAnimation: Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_in_right)
        var outAnimation: Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.slide_out_right)
        vfliper.inAnimation = inAnimation
        vfliper.outAnimation = outAnimation

    }
}