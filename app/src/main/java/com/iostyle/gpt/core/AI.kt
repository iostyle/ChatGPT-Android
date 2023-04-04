package com.iostyle.gpt.core

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.*
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
object AI {

    private val config = OpenAIConfig(
        token = "",
        timeout = Timeout(socket = 60.seconds)
    )

    val openAI = OpenAI(config)

    suspend fun singleQuestion(content: String): ChatMessage? {
        return withContext(Dispatchers.IO) {
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
                Logger.d(completion)
                return@withContext completion.choices[0].message
            }.onFailure {
                print(it.message)
            }
            return@withContext null
        }
    }

    val messageContext = arrayListOf<ChatMessage>()

    suspend fun contextualQuestionAppend(content: String): ChatMessage? {
        return withContext(Dispatchers.IO) {
            messageContext.add(
                ChatMessage(
                    role = ChatRole.User,
                    content = content
                )
            )
            Logger.d(messageContext)
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = messageContext
            )
            kotlin.runCatching {
                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
                Logger.d(completion)
                return@withContext completion.choices[0].message?.also {
                    messageContext.add(it)
                }
            }.onFailure {
                print(it.message)
            }
            return@withContext null
        }
    }
}