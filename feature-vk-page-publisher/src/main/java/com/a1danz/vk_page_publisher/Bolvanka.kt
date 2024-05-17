package com.a1danz.vk_page_publisher

import com.a1danz.vk_page_publisher.di.DaggerVkPagePublisherComponent
import com.a1danz.vk_page_publisher.di.VkPagePublisherComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class Bolvanka {
}

fun main(args: Array<String>) {
    val component: VkPagePublisherComponent = DaggerVkPagePublisherComponent.create()
    val vkApi = component.vkApi()
    val photo = File("C:\\Users\\Aidan\\AndroidStudioProjects\\PostingApp2\\feature-vk-page-publisher\\src\\main\\java\\com\\a1danz\\vk_page_publisher\\vk_icon.png")
    val requestFile: RequestBody = RequestBody.create(MultipartBody.FORM, photo)
    val body = MultipartBody.Part.createFormData("photo", photo.name, requestFile)
    val myApp = MyApplication()

    myApp.coroutineScope.launch {
        runCatching {
            val server = vkApi.getWallUploadServer()
            vkApi.savePhotoInServer(
                server.uploadServerDataModel.uploadUrl,
                body
            )
        }.onSuccess { saveResult ->
            println("SUCCESS")
            val result = vkApi.savePhotoInWall(
                218425683,
                saveResult.server,
                saveResult.photo,
                saveResult.hash
            )
            val post = vkApi.createPost(
                218425683,
                "test",
                "photo218425683_${result.wallPhotosList[0].id}"
            )
            println(post.postData.postId)
        }.onFailure {
            it.printStackTrace()
        }
    }

    runBlocking {
        delay(5000) // Например, ждем 5 секунд
    }
}

class MyApplication {
    private val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun stop() {
        job.cancel() // отменяем все корутины при завершении работы приложения
    }
}
