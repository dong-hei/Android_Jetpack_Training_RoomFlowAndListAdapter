package com.dk.roomflowandlistadapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.roomflowandlistadapter.databinding.ActivityMainBinding
import com.dk.roomflowandlistadapter.db.MyDB
import com.dk.roomflowandlistadapter.db.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MyDB.getDB(this)

        val recyclerView = binding.userRV
        val myListAdapter = MyListAdapter()

        recyclerView.adapter = myListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val name = binding.name.text.toString()
                val age = binding.age.text.toString()

                val userEntity = UserEntity(0, name, age.toInt())
                db.userDao().insert(userEntity)
            }
            }

        binding.get.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().getAllDataWithFlow().collect{

                    withContext(Dispatchers.Main){
                        myListAdapter.submitList(it)
                    }
                }
            }
        }

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        }
    }
}