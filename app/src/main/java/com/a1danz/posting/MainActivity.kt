package com.a1danz.posting

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.a1danz.posting.databinding.ActivityMainBinding
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var _viewBinding : ActivityMainBinding? = null
    private val viewBinding : ActivityMainBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        } catch (e : Exception) {
            println("TEST TAG - $e")
            throw e
        }
        setContentView(viewBinding.root)
        with(viewBinding) {
            println("TEST TAG - $mainFragmentContainer")
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        try {
            val a = super.onCreateView(name, context, attrs)
            return a
        } catch (e : Exception) {
            println("TEST TAG - $e")
            throw e
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


}