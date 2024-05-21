package com.a1danz.posting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.presentation.base.BaseActivity
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.domain.service.AuthorizationService
import com.a1danz.feature_create_post.domain.worker.PostPublishingWorker
import com.a1danz.feature_create_post.utils.WorkerCreator
import com.a1danz.feature_post_publisher_api.PostPublisher
import com.a1danz.feature_post_publisher_api.model.PostModel
import com.a1danz.posting.databinding.ActivityMainBinding
import com.a1danz.posting.di.DaggerAppComponent
import com.a1danz.posting.navigation.Navigator
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class MainActivity : BaseActivity(), WorkerCreator {
    override val fragmentContainerId: Int = R.id.main_fragment_container
    private var _viewBinding: ActivityMainBinding? = null
    private val viewBinding: ActivityMainBinding get() = _viewBinding!!

    @Inject lateinit var navigator : Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        navigator.attachNavController(getNavController())
    }

    override fun activateBnv() {
        viewBinding.bnv.let { bnv ->
            bnv.isVisible = true
            bnv.setupWithNavController(getNavController())
            navigator.navigateToMainScreen()
        }
    }

    override fun inject() {
        (application as App).appComponent.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detachNavController()
        _viewBinding = null
    }

    override fun createPostPublishingWorker(
        postPublisher: PostPublisher,
        postModel: PostModel
    ): UUID {
        val workRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<PostPublishingWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
        return workRequest.id
    }
}