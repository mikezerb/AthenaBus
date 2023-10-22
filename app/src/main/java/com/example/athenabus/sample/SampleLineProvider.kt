package com.example.athenabus.sample

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.athenabus.domain.model.Line

class SampleLineProvider : PreviewParameterProvider<Line> {
    override val values: Sequence<Line> = sequenceOf(
        Line(
            LineCode = "1151",
            LineID = "021",
            LineDescr = "ΠΛΑΤΕΙΑ ΚΑΝΙΓΓΟΣ - ΓΚΥΖH (ΚΥΚΛΙΚΗ)",
            LineDescrEng = "PLATEIA KANIGKOS - GKIZI",
            isFavorite = false
        ),
        Line(
            LineCode = "1574",
            LineID = "021",
            LineDescr = "ΠΛΑΤΕΙΑ ΚΑΝΙΓΓΟΣ - ΓΚΥΖH (ΑΠΟ ΓΚΥΖΗ)",
            LineDescrEng = "PLATEIA KANIGKOS - GKIZI (FROM GKIZI TO PLATEIA KANIGKOS)",
            isFavorite = false
        ),
        Line(
            LineCode = "1574",
            LineID = "21",
            LineDescr = "ΠΛΑΤΕΙΑ ΚΑΝΙΓΓΟΣ - ΓΚΥΖH (ΑΠΟ ΓΚΥΖΗ)",
            LineDescrEng = "PLATEIA KANIGKOS - GKIZI (FROM GKIZI TO PLATEIA KANIGKOS)",
            isFavorite = false
        ),
        Line(
            LineCode = "960",
            LineID = "608",
            LineDescr = "ΓΑΛΑΤΣΙ - ΑΚΑΔΗΜΙΑ - ΝΕΚΡ. ΖΩΓΡΑΦΟΥ",
            LineDescrEng = "GALATSI - AKADIMIA - NEKR. ZOGRAFOY",
            isFavorite = false
        ),
        Line(
            LineCode = "1447",
            LineID = "Α10",
            LineDescr = "ΣΤΑΘΜΟΣ ΛΑΡΙΣΗΣ - ΑΧΑΡΝΑΙ (ΕΩΣ ΣΤΑΣΗ ΚΑΡΑΒΟΣ ΜΕΣΩ ΑΓ.ΠΕΤΡΟΥ)",
            LineDescrEng = "STATHMOS LARISIS - ACHARNAI (TO KARABOS STOP VIA AG.PETROU)",
            isFavorite = false
        ),
        Line(
            LineCode = "1642",
            LineID = "909",
            LineDescr = "ΑΓ. ΒΑΣΙΛΕΙΟΣ - ΑΓ. ΣΟΦΙΑ - ΚΡΑΤ. ΝΙΚΑΙΑΣ (ΔΕ-ΠΕ 20:00-23:59 ΠΑ-ΚΥ 18:00-23:59)",
            LineDescrEng = "AG. VASILEIOS - AG. SOFIA - KRAT. NIKAIAS (MO-TH 20:00-23:59, FR-SU 18",
            isFavorite = false
        ),
    )
}