import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.athenabus.domain.model.Line

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuSelection(
    modifier: Modifier = Modifier,
    initialText: String,
    itemList: List<String> = emptyList(),
    selectedOption: String = "",
    onClick: (String, Int) -> Unit,
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit = { },
    onDismiss: () -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpanded,
    ) {
        OutlinedTextField(
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
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            expanded = expanded,
            onDismissRequest = onDismiss
        )
        {
            itemList.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(text = item)
                    },
                    onClick = {
                        onClick(item, index)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectItemPreview() {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember {
        mutableStateOf("")
    }
    var selectedLine by remember {
        mutableIntStateOf(0)
    }

    val context = LocalContext.current

    val routes = listOf(
        Line(
            LineID = "123", LineCode = "123", LineDescr = "APO - PROS", LineDescrEng = "",
            isFavorite = false
        ),
        Line(
            LineID = "123", LineCode = "456", LineDescr = "PROS - APO", LineDescrEng = "",
            isFavorite = false
        ),
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        DropdownMenuSelection(
            itemList = routes.map { it.LineDescr },
            initialText = "Choose direction",
            onDismiss = { expanded = false },
            onClick = { line, index ->
                selected = line
                selectedLine = index
                expanded = false
                Toast.makeText(
                    context,
                    "Selected ${routes[selectedLine].LineCode}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            selectedOption = selected,
            onExpanded = { expanded = !expanded },
            expanded = expanded
        )
    }
}
