package com.gobinda.assignment.keeper.ui.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.gobinda.assignment.keeper.domain.model.Product
import com.gobinda.assignment.keeper.domain.model.Section
import com.gobinda.assignment.keeper.domain.model.SectionType

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val sectionLazyPagingItems = viewModel.sections.collectAsLazyPagingItems()

    HomeContent(modifier, sectionLazyPagingItems)
}


@Composable
private fun HomeContent(
    modifier: Modifier,
    sectionLazyPagingItems: LazyPagingItems<Section>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(sectionLazyPagingItems.itemCount) {
            sectionLazyPagingItems[it]?.apply {
                when (sectionType) {
                    SectionType.BANNER -> Banner(products)
                    SectionType.HORIZONTAL_FREE_SCROLL -> Horizontal(
                        products
                    )

                    SectionType.SPLIT_BANNER -> {
                        SplitBanner(products)
                    }

                    null -> {}
                }
            }


        }
    }


    sectionLazyPagingItems.apply {
        when {
            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }

            }

            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                Text(text = "Error")
            }

            loadState.refresh is LoadState.NotLoading -> {

            }
        }
    }
}


@Composable
fun Banner(products: List<Product>) {
        AsyncImage(
            model = products.firstOrNull()?.image ,
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .size(size = 240.dp)
        )
}

@Composable
fun Horizontal(products: List<Product>) {
    LazyRow() {
        items(products.size) {
           Column(modifier = Modifier.width(124.dp)) {
               AsyncImage(
                   model = products[it].image ?: "-",
                   contentDescription = "",
                   contentScale = ContentScale.FillBounds,
                   modifier = Modifier.size(size = 124.dp)
               )
               Text(text = products[it].title ?: "",
                   modifier = Modifier.padding(5.dp)
                   , maxLines = 1)
           }

        }
    }

}

@Composable
fun SplitBanner(products: List<Product>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(240.dp)) {
        items(products.size) {
            AsyncImage(
                model = products[it].image ?: "-",
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(size = 240.dp)
            )
        }

    }
}


