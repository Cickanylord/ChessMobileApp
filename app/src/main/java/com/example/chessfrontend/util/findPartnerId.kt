package com.example.chessfrontend.util

import com.example.chessfrontend.ui.model.MatchUiModel
import com.example.chessfrontend.ui.model.UserUiModel

fun findPartnerId(
    user: UserUiModel,
    match: MatchUiModel
): Long =
    if (user.id == match.challenged) {
        match.challenger

    } else {
        match.challenged
    }
