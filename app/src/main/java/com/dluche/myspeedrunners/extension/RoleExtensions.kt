package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.RoleEnum

@Composable
fun RoleEnum.getTranslation(): String {
    return stringResource(
        when (this) {
            RoleEnum.USER -> R.string.role_user
            RoleEnum.MODERATOR -> R.string.role_moderator
            RoleEnum.ADMIN -> R.string.role_admin
            RoleEnum.UNKNOWN -> R.string.role_unknown
            RoleEnum.BANNED -> R.string.role_banned
            RoleEnum.TRUSTED -> R.string.role_trusted
            RoleEnum.PROGRAMMER -> R.string.role_programmer
        }
    )
}