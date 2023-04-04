package com.iostyle.gpt.core

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.*
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
object AI {
    private val config = OpenAIConfig(
        token = "",
        timeout = Timeout(socket = 60.seconds)
    )
    val openAI = OpenAI(config)

    suspend fun singleQuestion(content: String): ChatMessage? {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.User,
                    content = content
                )
            )
        )
        kotlin.runCatching {
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            return completion.choices[0].message
        }.onFailure {
            print(it.message)
        }
        return null
    }
}