package com.example.athenabus.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.athenabus.domain.model.Route

class SampleRouteProvider : PreviewParameterProvider<Route> {
    override val values: Sequence<Route> = sequenceOf(
        Route(
            RouteCode = "2484",
            LineCode = "1151",
            RouteDescr = "ΠΛΑΤΕΙΑ ΚΑΝΙΓΓΟΣ - ΓΚΥΖΗ",
            RouteDescrEng = "PLATEIA KANIGGOS - GKYZI",
            RouteDistance = "1",
            RouteType = "4933.76"
        ),
        Route(
            RouteCode = "4198",
            LineCode = "1574",
            RouteDescr = "ΠΛΑΤΕΙΑ ΚΑΝΙΓΓΟΣ - ΓΚΥΖH [ΓΚΥΖΗ ΕΩΣ ΚΑΝΙΓΓΟΣ]",
            RouteDescrEng = "PLATEIA KANIGGOS - GKYZI",
            RouteDistance = "1",
            RouteType = "2531.46"
        ),
        Route(
            RouteCode = "3091",
            LineCode = "804",
            RouteDescr = "ΣΤ.ΚΑΤΕΧΑΚΗ - ΣΤ.ΠΑΝΟΡΜΟΥ - ΓΑΛΑΤΣΙ - ΚΥΨΕΛΗ",
            RouteDescrEng = "PLATEIA KANIGGOS - GKYZI",
            RouteDistance = "1",
            RouteType = "16879.4"
        ),
        Route(
            RouteCode = "3092",
            LineCode = "804",
            RouteDescr = "ΣΤ.ΚΑΤΕΧΑΚΗ-ΣΤ.ΠΑΝΟΡΜΟΥ-ΓΑΛΑΤΣΙ-ΚΥΨΕΛΗ [Εναλλακτική λόγω λαϊκής κάθε Τρίτη 05:00-17:00]",
            RouteDescrEng = "PLATEIA KANIGGOS - GKYZI",
            RouteDistance = "1",
            RouteType = "16921.44"
        ),
        Route(
            RouteCode = "3093",
            LineCode = "1219",
            RouteDescr = "ΣΤ.ΚΑΤΕΧΑΚΗ-ΣΤ.ΠΑΝΟΡΜΟΥ-ΓΑΛΑΤΣΙ-ΚΥΨΕΛΗ [ΜΕΧΡΙ ΠΛΑΤΕΙΑ ΚΥΨΕΛΗΣ]",
            RouteDescrEng = "PLATEIA KANIGGOS - GKYZI",
            RouteDistance = "1",
            RouteType = "9048.77"
        ),
    )
}