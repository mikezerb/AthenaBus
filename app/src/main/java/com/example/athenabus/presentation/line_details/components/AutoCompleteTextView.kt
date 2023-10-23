package com.example.athenabus.presentation.line_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    initialText: String,
    itemList: List<String> = emptyList(),
    mapList: Map<String, String> = emptyMap(),
    routes: List<Route> = emptyList<Route>(),
    selectedOption: String = "",
    onSelect: (Route) -> Unit = { },
    onClick: () -> Unit,
    onClearResults: () -> Unit = { },
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit = { },
    onDismiss: () -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpanded ,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = selectedOption,
            onValueChange = { },
            label = { Text(text = initialText) },
            readOnly = true,
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            textStyle = MaterialTheme.typography.titleSmall,
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            expanded = expanded,
            onDismissRequest = onDismiss
        )
        {
            routes.forEach { route ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(text = route.RouteDescr)
                    },
                    onClick = {
                        onSelect(route)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var expanded by remember { mutableStateOf(false) }
    var selectedRoute by remember {
        mutableStateOf("")
    }
    var selectedRouteCode by remember {
        mutableStateOf("")
    }
    val routes = listOf(
        Route(
            RouteCode = "123", LineCode = "", RouteDescr = "Route 1", RouteDescrEng = "",
            RouteDistance = "", LineID = "", RouteType = "", LineDescr = "", LineDescrEng = "",
            MasterLineCode = "", hidden = ""
        ),
        Route(
            RouteCode = "456", LineCode = "", RouteDescr = "Route 2", RouteDescrEng = "",
            RouteDistance = "", LineID = "", RouteType = "", LineDescr = "", LineDescrEng = "",
            MasterLineCode = "", hidden = ""
        ),
    )
    Column(Modifier.fillMaxSize()) {
        AutoCompleteTextField(
            routes = routes,
            initialText = "Choose direction",
            onDismiss = { expanded = false },
            onClick = { expanded = true },
            onSelect = { i ->
                selectedRoute = i.RouteDescr
                selectedRouteCode = i.RouteCode
                expanded = false
            },
            selectedOption = selectedRoute,
            onExpanded = { expanded = !expanded },
            expanded = expanded
        )
    }
}
