package com.a1danz.posting.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.a1danz.posting.databinding.FragmentNextBinding

class NextFragment : Fragment() {
    var _viewBinding : FragmentNextBinding? = null
    val viewBinding : FragmentNextBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNextBinding.inflate(inflater)

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}