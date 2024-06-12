package com.example.athenabus.data.local

import com.example.athenabus.R
import com.example.athenabus.presentation.onboarding.Page

object PageData {
    val pages = listOf(
        Page(
            "Άνετη πλοήγηση",
            "Περιηγηθείτε την Αθήνα με ευκολία με την κομψή και εύχρηστη διεπαφή μας",
            R.drawable.people_map
        ),
        Page(
            "Πάντα ενήμεροι πριν τη διαδρομή σας",
            "Δείτε αφίξεις σε πραγματικό χρόνο, δρομολόγια και στάσεις για όλες τις αγαπημένες σας γραμμές",
            R.drawable.location
        ),
        Page(
            "Κάντε κάθε βόλτα ένα παιχνιδάκι",
            "Αποθηκεύοντας γραμμές λεωφορείων και στάσεις που χρησιμοποιείτε συχνά",
            R.drawable.man_phone
        )
    )
}