package com.a1danz.feature_create_post.presentation.mapper

import com.a1danz.feature_create_post.presentation.model.ImageUiModel
import com.esafirm.imagepicker.model.Image

fun Image.toImageUiModel(): ImageUiModel {
    return ImageUiModel(this.uri)
}