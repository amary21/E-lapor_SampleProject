package com.amary21.e_lapor

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amary21.e_lapor.ui.lapor.LaporFragment
import com.amary21.e_lapor.ui.sukses.SuksesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReadActivity : AppCompatActivity() {

    private val selectedMenu = "com.amary21.e_lapor.Menu"
    private lateinit var navView : BottomNavigationView

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_proses ->{
                    val fragment = LaporFragment.newInstance()
                    changeFragment(fragment, LaporFragment::class.java.simpleName)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_success ->{
                    val fragment = SuksesFragment.newInstance()
                    changeFragment(fragment, SuksesFragment::class.java.simpleName)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    @SuppressLint("PrivateResource")
    private fun changeFragment(fragment: Fragment, simpleName: String) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )

        val currentFragment = mFragmentManager.primaryNavigationFragment

        if (currentFragment != null){
            fragmentTransaction.hide(currentFragment)
        }

        var fragmentTemp = mFragmentManager.findFragmentByTag(simpleName)
        if (fragmentTemp == null){
            fragmentTemp = fragment
            fragmentTransaction.add(R.id.nav_host_fragment, fragment, simpleName)
        }else{
            fragmentTransaction.show(fragmentTemp)
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        navView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState != null){
            savedInstanceState.getInt(selectedMenu)
        }else{
            val fragment = LaporFragment.newInstance()
            changeFragment(fragment, LaporFragment::class.java.simpleName)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(selectedMenu, navView.selectedItemId)
    }
}
