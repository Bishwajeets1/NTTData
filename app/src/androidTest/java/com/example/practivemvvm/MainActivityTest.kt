package com.example.practivemvvm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import org.junit.Rule
import org.junit.Test
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Assert.assertEquals


class MainActivityTest {


    @get:Rule
    val activityScenarioRule = createComposeRule()


    // 1. Rendering Test
    @Test
    fun lazyColumn_rendersItemsCorrectly() {
        val items = listOf("Item 1", "Item 2", "Item 3")
        activityScenarioRule.setContent {
            LazyColumn {
                items(items) { item ->
                    Text(text = item)
                }
            }
        }

        items.forEachIndexed { index, item ->
            activityScenarioRule.onNodeWithText(item).assertExists()
        }
    }

    //2. Scrolling Test

    @Test
    fun lazyColumn_scrollsCorrectly() {
        val items = List(100) { "Item $it" }
        var lastVisibleItem = -1
        activityScenarioRule.setContent {
            LazyColumn {
                items(items) { item ->
                    Text(text = item)
                    lastVisibleItem++
                }
            }
        }

        activityScenarioRule.waitForIdle()
        // Verify the first item is displayed
        activityScenarioRule.onNodeWithText("Item 0").assertExists()
        // Perform a scroll action
        activityScenarioRule.onNodeWithText("Item 0").performScrollTo()
        // Verify the last item is displayed after scrolling
       // activityScenarioRule.onNodeWithText("Item 99").assertExists()
        activityScenarioRule.onNodeWithText("Item $lastVisibleItem").performScrollTo().assertExists()
    }

    @Test
    fun lazyColumn_clickItem() {
        var clickedItem: String? = null
        val items = listOf("Item 1", "Item 2", "Item 3")
        activityScenarioRule.setContent {
            LazyColumn {
                items(items) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .clickable { clickedItem = item }
                    )
                }
            }
        }

        // Perform click on the second item
        activityScenarioRule.onNodeWithText("Item 2").performClick()

        // Verify the clicked item is the correct one
        assertEquals("Item 2", clickedItem)
    }


    @Test
    fun lazyColumn_itemStateChange() {
        data class Item(val id: Int, var isSelected: Boolean)

        val items = mutableListOf(
            Item(1, false),
            Item(2, false),
            Item(3, false)
        )

        activityScenarioRule.setContent {
            LazyColumn {
                items(items) { item ->
                    var layoutResult by remember { mutableStateOf(false) }

                    Text(
                        text = if (layoutResult) "Selected" else "Not Selected",
                        modifier = Modifier
                            .clickable {
                                layoutResult = !layoutResult
                                item.isSelected = !item.isSelected
                            }
                    )
                }
            }
        }

        // Perform click on the first item to change its state
        activityScenarioRule.onAllNodes(hasClickAction())[0].performClick()

        // Verify the item's state is updated
        activityScenarioRule.onNodeWithText("Selected").assertExists()
    }

    @Test
    fun lazyColumn_itemContent() {
        val items = listOf("Item 1", "Item 2", "Item 3")
        activityScenarioRule.setContent {
            LazyColumn {
                items(items) { item ->
                    Row {
                        Text(text = item)
                        Text(text = "$item Description")
                    }
                }
            }
        }

        // Verify the items display the correct content
        items.forEach { item ->
            activityScenarioRule.onNodeWithText("$item Description").assertExists()
        }
    }


}

