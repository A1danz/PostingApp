package com.a1danz.feature_create_post.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewPostPublishingDialogBinding
import com.a1danz.feature_create_post.presentation.model.PostPlaceDetailInfoUiModel
import com.a1danz.feature_create_post.presentation.rv.PostPlacesAdapter

class PostPublishingDialogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_post_publishing_dialog, this)
    }

    private val viewBinding: ViewPostPublishingDialogBinding by viewBinding(ViewPostPublishingDialogBinding::bind)

    fun setText(text: String) {
        if (text.isNotBlank()) {
            viewBinding.editText.setText(text)
        }
    }

    fun addCorrection(correctionMsg: String) {
        viewBinding.layoutCorrections.addView(
            CorrectionView(context, null).apply {
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                setText(correctionMsg)
            }
        )
    }

    fun setMediaCount(count: Int) {
        viewBinding.tvMediaCount.text = count.toString()
    }

    fun setContinueCallback(callback: () -> Unit) {
        viewBinding.btnContinue.setOnClickListener {
            callback.invoke()
        }
    }

    fun setCancelCallback(callback: () -> Unit) {
        viewBinding.btnCancel.setOnClickListener {
            callback.invoke()
        }
    }

    fun setPostPlacesDetailInfo(places: List<PostPlaceDetailInfoUiModel>) {
        val adapter = PostPlacesAdapter(places)
        viewBinding.rvPlaces.adapter = adapter
    }
}