package com.dluche.myspeedrunners.ui.previewparameter

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum

class RunStatusPreviewParam: PreviewParameterProvider<RunStatusEnum> {
    override val values: Sequence<RunStatusEnum> = sequenceOf(
        RunStatusEnum.NEW,
        RunStatusEnum.VERIFIED,
        RunStatusEnum.REJECTED,
        RunStatusEnum.UNKNOWN
    )
}