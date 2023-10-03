package com.example.athenabus.domain.use_case.bus_lines

import com.example.athenabus.domain.model.Line
import javax.inject.Inject

class GetLinesFromSearchUseCase @Inject constructor(
) {

    operator fun invoke(busLines: List<Line>, query: String): List<Line> {
        // Filter the bus lines based on the search query
        return busLines.filter { busLine ->
            busLine.LineID.contains(query, ignoreCase = true)
            // For more accurate results we filter only with lineID
//                    || busLine.LineDescr.contains(query, ignoreCase = true) ||
//                    busLine.LineDescrEng.contains(query, ignoreCase = true)

        }
    }

}