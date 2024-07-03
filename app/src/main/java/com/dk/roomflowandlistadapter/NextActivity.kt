package com.dk.roomflowandlistadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dk.roomflowandlistadapter.databinding.ActivityNextBinding
import com.dk.roomflowandlistadapter.db.MyDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NextActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MyDB.getDB(this)
        val rv = binding.userRV
        val myListAdapter = MyListAdapter()

        rv.adapter = myListAdapter
        rv.layoutManager = LinearLayoutManager(this)

        binding.readBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().getAllDataWithFlow().collect{

                    withContext(Dispatchers.Main){
                        myListAdapter.submitList(it)
                    }
                }
            }
        }

        binding.updateBtn.setOnClickListener {
            val id = binding.id.text.toString().toInt()
            CoroutineScope(Dispatchers.IO).launch {
                val result = db.userDao().getAllData()
                val userEntity = result[id]
                userEntity.name = "updated 된 it"
                userEntity.age = 0
                db.userDao().update(userEntity)
            }

            }

        binding.removeBtn.setOnClickListener {
            val id = binding.id.text.toString().toInt()
            CoroutineScope(Dispatchers.IO).launch {
                val result = db.userDao().getAllData()
                val userEntity = result[id]

                db.userDao().delete(userEntity)

        }

        }

    }
}