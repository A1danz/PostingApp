package com.a1danz.feature_create_post.presentation.model

import android.net.Uri
import com.esafirm.imagepicker.model.Image
import java.io.Serializable

data class ImageUiModel (
    val imgUri: Uri
) : Serializable