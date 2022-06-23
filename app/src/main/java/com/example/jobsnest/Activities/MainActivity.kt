package com.example.jobsnest.Activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.jobsnest.R
import com.example.jobsnest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom) }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.expand.setOnClickListener{
            onExpandClicked()

        }
        mainBinding.addAccountName.setOnClickListener {
            Toast.makeText(this, "To be Implemented", Toast.LENGTH_SHORT).show()
        }
        mainBinding.newJob.setOnClickListener {
            startActivity(Intent(this,AddWorkActivity::class.java))
            finish()
        }

    }

    private fun onExpandClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked : Boolean) {
        if (clicked){
            mainBinding.newJob.isVisible = false
            mainBinding.addAccountName.isVisible = false
        }else{
            mainBinding.newJob.visibility = View.VISIBLE
            mainBinding.addAccountName.visibility = View.VISIBLE
        }
    }

    private fun setAnimation(clicked : Boolean) {
        if (!clicked){
            mainBinding.newJob.startAnimation(fromBottom)
            mainBinding.addAccountName.startAnimation(fromBottom)
            mainBinding.expand.startAnimation(rotateOpen)
        }else{
            mainBinding.newJob.startAnimation(toBottom)
            mainBinding.addAccountName.startAnimation(toBottom)
            mainBinding.expand.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked){
            mainBinding.newJob.isClickable = true
            mainBinding.addAccountName.isClickable = true
        }else{
            mainBinding.newJob.isClickable = false
            mainBinding.addAccountName.isClickable = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return  true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            R.id.logout->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.addaccount->{
                true
            }
            R.id.netIncome->{
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }


}