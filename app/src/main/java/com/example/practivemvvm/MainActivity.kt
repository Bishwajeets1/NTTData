package com.example.practivemvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import coil.compose.AsyncImage
import com.example.practivemvvm.model.DataModelClass
import com.example.practivemvvm.model.Photos
import com.example.practivemvvm.ui.theme.PractiveMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val dataViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PractiveMVVMTheme {
               SetUi()
            }
        }
    }

    @Composable
    private fun SetUi() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {

            Column {
                FilterButton {
                    dataViewModel.filterByLanguage(it)
                }
                Spacer(modifier = Modifier.height(50.dp))
                val dataViewModelPhotos by dataViewModel._photos.collectAsState()
                val errorMessage by dataViewModel.errorMessage.collectAsState()

                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn{
                        itemsIndexed(items = dataViewModelPhotos){ index, item ->
                            HeadingText(item)
                        }
                    }
                }

            }
        }
        dataViewModel.getData()
    }

    @Composable
    private fun HeadingText(photos: DataModelClass) {
        Column( horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth().clickable {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra("URL", photos.htmlUrl)
            }
            startActivity(intent)
        }) {
            Text(text = photos.name.toString(), modifier = Modifier.wrapContentSize(),
                style = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }


    @Composable
    fun FilterButton(onFilterSelected: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }
        var selectedFilter by remember { mutableStateOf("All") } // Initial text for the button

        Box(modifier = Modifier.wrapContentSize()) {
            // Button to open the dropdown
            Button(onClick = { expanded = true }) {
                Text(text = selectedFilter)  // Provide the selected filter as text
            }

            // Dropdown menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.wrapContentSize()
            ) {

                DropdownMenuItem(onClick = {
                    selectedFilter = MainActivityViewModel.ProgrammingLanguage.ALL.languageText
                    expanded = false
                    onFilterSelected(MainActivityViewModel.ProgrammingLanguage.ALL.languageText)
                }, text = {
                    Text(MainActivityViewModel.ProgrammingLanguage.ALL.languageText)
                })

                DropdownMenuItem(onClick = {
                    selectedFilter = MainActivityViewModel.ProgrammingLanguage.JAVA.languageText
                    expanded = false
                    onFilterSelected( MainActivityViewModel.ProgrammingLanguage.JAVA.languageText)
                }, text = {
                    Text( MainActivityViewModel.ProgrammingLanguage.JAVA.languageText)
                })

                DropdownMenuItem(onClick = {
                    selectedFilter =  MainActivityViewModel.ProgrammingLanguage.KOTLIN.languageText
                    expanded = false
                    onFilterSelected(MainActivityViewModel.ProgrammingLanguage.KOTLIN.languageText)
                }, text = {
                    Text(MainActivityViewModel.ProgrammingLanguage.KOTLIN.languageText)
                })
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun FilterButtonPreview() {
        FilterButton(onFilterSelected = { selectedFilter ->
            // Handle the selected filter (e.g., Java or Kotlin)
            println("Selected filter: $selectedFilter")
        })
    }



    @Preview
    @Composable
    private fun SetUis() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column {
                val sampleDataModel = listOf(
                    DataModelClass(name = "Sample 1"),
                    DataModelClass(name = "Sample 2"),
                    DataModelClass(name = "Sample 3")
                )

                LazyColumn {
                    items(sampleDataModel) { item ->
                        HeadingText(item)
                    }
                }
            }
        }
    }
}