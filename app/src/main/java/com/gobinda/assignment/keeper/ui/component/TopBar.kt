package com.gobinda.assignment.keeper.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(
    title: String
) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors()
    )
}

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview() {
    TitleTopBar(title = "Home")
}