package com.a1danz.feature_settings.presentation.screens.main

import com.a1danz.common.config.AppConfig
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.intent.IntentManager
import com.a1danz.common.presentation.base.BaseViewModel
import com.a1danz.common.presentation.base.model.AlertDialogData
import com.a1danz.common.presentation.base.model.ButtonData
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.presentation.navigation.SettingsRouter
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRouter: SettingsRouter,
    private val intentManager: IntentManager,
    private val resourcesManager: ResourceManager,
) : BaseViewModel() {

    fun navigateToSocialMediaSettingsScreen() {
        settingsRouter.openSocialMediaSettings()
    }

    fun navigateToAboutScreen() {
        settingsRouter.openAboutScreen()
    }

    private fun openFeedbackLink() {
        intentManager.getTelegramIntent(AppConfig.TG_FEEDBACK_USERNAME).let { intent ->
            if (intent == null) {
                _baseUiEvent.emitAlertDialogEvent(
                    AlertDialogData(
                        title = resourcesManager.getString(com.a1danz.common.R.string.error_default_title),
                        message = resourcesManager.getString(R.string.cant_find_programm_to_open_telegram),
                        positiveButton = ButtonData(text = resourcesManager.getString(R.string.ok))
                    )
                )
            } else {
                _baseUiEvent.emitShowIntentEvent(intent)
            }
        }
    }

    fun onFeedbackClicked() {
        _baseUiEvent.emitAlertDialogEvent(
            AlertDialogData(
                title = resourcesManager.getString(R.string.feedback),
                message = resourcesManager.getString(R.string.feedback_description),
                positiveButton = ButtonData(resourcesManager.getString(R.string.open), ::openFeedbackLink),
                negativeButton = ButtonData(resourcesManager.getString(R.string.cancel)) { }
            )
        )
    }
}