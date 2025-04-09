package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum

@Composable
fun RunStatusEnum.getTranslation(): String {
    return stringResource(
        id = when (this) {
            RunStatusEnum.NEW -> R.string.run_status_new
            RunStatusEnum.VERIFIED -> R.string.run_status_verified
            RunStatusEnum.REJECTED -> R.string.run_status_reject
            RunStatusEnum.UNKNOWN -> R.string.run_status_unknown
        }
    )
}