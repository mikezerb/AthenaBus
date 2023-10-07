package com.example.athenabus.presentation.closest_stops.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Stop
import com.example.athenabus.presentation.closest_stops.components.defaults.CircleParameters
import com.example.athenabus.presentation.closest_stops.components.defaults.LineParameters
import com.example.athenabus.presentation.closest_stops.components.defaults.LineParametersDefaults.linearGradient
import com.example.athenabus.presentation.closest_stops.components.defaults.circleParameters
import com.example.athenabus.sample.SampleStopProvider

@Composable
fun StopLineNode(
    position: StopLineNodePosition,
    contentStartOffset: Dp = 16.dp,
    spacer: Dp = 16.dp,
    circleParameters: CircleParameters = CircleParameters(
        12.dp,
        MaterialTheme.colorScheme.surfaceTint
    ),
    lineParameters: LineParameters? = null,
    content: @Composable BoxScope.(modifier: Modifier) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                // 2. draw a circle here ->
                val circleRadiusInPx = circleParameters.radius.toPx()

                lineParameters?.let {
                    drawLine(
                        brush = it.brush,
                        start = Offset(x = circleRadiusInPx, y = circleRadiusInPx * 2),
                        end = Offset(x = circleRadiusInPx, y = this.size.height),
                        strokeWidth = it.strokeWidth.toPx()
                    )
                }
                drawCircle(
                    circleParameters.backgroundColor,
                    circleRadiusInPx,
                    center = Offset(x = circleRadiusInPx, y = circleRadiusInPx)
                )

                circleParameters.stroke?.let { stroke ->
                    val strokeWidthInPx = stroke.width.toPx()
                    drawCircle(
                        color = stroke.color,
                        radius = circleRadiusInPx - strokeWidthInPx / 2,
                        center = Offset(x = circleRadiusInPx, y = circleRadiusInPx),
                        style = Stroke(width = strokeWidthInPx)
                    )
                }
            }
    ) {
        content(
            Modifier
                .defaultMinSize(minHeight = circleParameters.radius * 2)
                .padding(
                    start = circleParameters.radius * 2 + contentStartOffset,
                    bottom = if (position != StopLineNodePosition.LAST) spacer else 0.dp
                )
        )
    }
}

enum class StopLineNodePosition {
    FIRST,
    MIDDLE,
    LAST
}

@Preview(showBackground = true)
@Composable
fun StopLinePreview(@PreviewParameter(SampleStopProvider::class) stop: Stop) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        StopLineNode(
            position = StopLineNodePosition.FIRST,
            circleParameters = circleParameters(
                backgroundColor = MaterialTheme.colorScheme.surfaceTint
            ),
            lineParameters = linearGradient(
                startColor = MaterialTheme.colorScheme.surfaceTint,
                endColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) { modifier ->
            ClosestStopItem(modifier = modifier.align(Alignment.Center), stop = Stop(
                StopCode = "124",
                StopDescr = "Δοκιμή Στάσης",
                StopDescrEng = "",
                StopID = "602",
                StopStreet = "ΠΕΙΡΑΙΩΣ",
                StopLat = "38.0400",
                StopLng = "23.7614",
                distance = "0.004502298261997398"
            ), onClick = { })
        }
        StopLineNode(
            position = StopLineNodePosition.MIDDLE,
            circleParameters = circleParameters(
                backgroundColor = MaterialTheme.colorScheme.surfaceTint
            ),
            lineParameters = linearGradient(
                startColor = MaterialTheme.colorScheme.surfaceTint,
                endColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) { modifier ->
            ClosestStopItem(modifier = modifier, stop = Stop(
                StopCode = "124",
                StopDescr = "Επόμενη στάση",
                StopDescrEng = "",
                StopID = "602",
                StopLat = "38.0400",
                StopLng = "23.7614",
                StopStreet = "ΠΕΙΡΑΙΩΣ",
                distance = "0.004502298261997398"
            ), onClick = { })
        }
        StopLineNode(position = StopLineNodePosition.LAST) { modifier ->
            ClosestStopItem(
                modifier = modifier,
                stop = Stop(
                    StopCode = "124",
                    StopDescr = "Τελευταία στάση",
                    StopDescrEng = "",
                    StopID = "602",
                    StopLat = "38.0400",
                    StopLng = "23.7614",
                    StopStreet = "ΠΕΙΡΑΙΩΣ",
                    distance = "0.004502298261997398"
                ),
                onClick = { }
            )
        }
    }
}