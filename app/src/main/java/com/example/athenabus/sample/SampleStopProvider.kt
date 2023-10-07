package com.example.athenabus.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.athenabus.domain.model.Stop

class SampleStopProvider : PreviewParameterProvider<Stop> {
    override val values: Sequence<Stop> = sequenceOf(
        Stop(
            StopCode = "124",
            StopDescr = "ΣΤΡΟΦΗ ΠΕΙΡΑΙΩΣ",
            StopDescrEng = "",
            StopID = "602",
            StopStreet = "ΠΕΙΡΑΙΩΣ",
            StopLng = "23.7614",
            StopLat = "38.0400",
            distance = "0.004502298261997398"
        )
    )
}