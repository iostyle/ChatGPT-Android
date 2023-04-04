package com.iostyle.gpt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.iostyle.gpt.BuildConfig
import com.iostyle.gpt.adapter.GPTAdapter
import com.iostyle.gpt.core.AI
import com.iostyle.gpt.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(BetaOpenAI::class)
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
                Toast.makeText(applicationContext, "请输入内容", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.sendButton.text = "AI思考中"
            binding.sendButton.isEnabled = false
            binding.contentEt.setText("")
            adapter.add(ChatMessage(role = ChatRole.User, content = content))
            lifecycleScope.launchWhenResumed {
                AI.contextualQuestionAppend(content)?.let { msg ->
                    adapter.add(msg)
                }
                binding.sendButton.text = "发送"
                binding.sendButton.isEnabled = true
            }
        }

    }
}
