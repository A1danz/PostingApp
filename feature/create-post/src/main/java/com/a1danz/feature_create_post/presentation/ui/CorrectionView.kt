package com.a1danz.feature_create_post.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewCorrectionBinding

class CorrectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_correction, this)
    }

    val viewBinding: ViewCorrectionBinding by viewBinding(ViewCorrectionBinding::bind)

    fun setText(text: String) {
        viewBinding.tvText.text = text
    }
}