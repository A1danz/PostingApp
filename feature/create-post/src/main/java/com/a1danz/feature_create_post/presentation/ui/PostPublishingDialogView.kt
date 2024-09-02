package com.a1danz.feature_create_post.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.feature_create_post.R
import com.a1danz.feature_create_post.databinding.ViewPostPublishingDialogBinding
import com.a1danz.feature_create_post.presentation.model.PostUiModel
import com.a1danz.feature_create_post.presentation.rv.post_destinations.PostDestinationsAdapter

class PostPublishingDialogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_post_publishing_dialog, this)
    }

    private val viewBinding: ViewPostPublishingDialogBinding by viewBinding(ViewPostPublishingDialogBinding::bind)

    fun setupData(
        postInfoUiModel: PostUiModel,
        corrections: List<String>,
        cancelCallback: () -> Unit,
        continueCallback: () -> Unit,
    ) {
        with(viewBinding) {
            if (postInfoUiModel.text.isNotBlank()) editText.setText(postInfoUiModel.text)

            tvMediaCount.text = postInfoUiModel.images.size.toString()

            corrections.forEach(::addCorrection)

            rvDestinations.adapter = PostDestinationsAdapter(postInfoUiModel.destinations)

            btnCancel.setOnClickListener {
                cancelCallback.invoke()
            }

            btnContinue.setOnClickListener {
                continueCallback.invoke()
            }
        }
    }

    private fun addCorrection(correctionMsg: String) {
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
}