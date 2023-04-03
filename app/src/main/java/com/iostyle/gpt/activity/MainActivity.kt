package com.iostyle.gpt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.iostyle.gpt.BuildConfig
import com.iostyle.gpt.adapter.GPTAdapter
import com.iostyle.gpt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GPTAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVersion()
        initRecycler()
        initListener()
    }

    private fun initVersion() {
        binding.versionTv.text = BuildConfig.VERSION_NAME

    }

    private fun initRecycler() {
        adapter = GPTAdapter(arrayListOf())
        binding.recycler.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@MainActivity.adapter
        }
    }

    private fun initListener() {
        binding.sendButton.setOnClickListener {
            val content = binding.contentEt.text.toString()
            if (content.isEmpty()) {
                Toast.makeText(applicationContext, "请输入", Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

        }

    }
}
