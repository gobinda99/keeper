package com.gobinda.assignment.keeper.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.gobinda.assignment.keeper.R
import com.gobinda.assignment.keeper.domain.model.Product
import com.gobinda.assignment.keeper.domain.model.Section
import com.gobinda.assignment.keeper.domain.model.SectionType
import com.gobinda.assignment.keeper.ui.component.ErrorView
import com.gobinda.assignment.keeper.ui.component.LoadingView
import com.gobinda.assignment.keeper.ui.component.TitleTopBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val sectionLazyPagingItems = viewModel.sections.collectAsLazyPagingItems()

    HomeContent(modifier, sectionLazyPagingItems) {
        viewModel.onRetry()
    }
}


@Composable
private fun HomeContent(
    modifier: Modifier,
    sectionLazyPagingItems: LazyPagingItems<Section>, retry: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(title = stringResource(id = R.string.home))
        }) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            items(sectionLazyPagingItems.itemCount) {
                sectionLazyPagingItems[it]?.apply {
                    when (sectionType) {
                        SectionType.BANNER -> BannerProduct(products)
                        SectionType.HORIZONTAL_FREE_SCROLL ->
                            HorizontalFreeScrollProducts(
                                products
                            )

                        SectionType.SPLIT_BANNER -> {
                            SplitBannerProducts(products)
                        }

                        null -> {}
                    }
                }

            }
        }
        sectionLazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading
                        || loadState.append is LoadState.Loading -> {
                    LoadingView()
                }

                loadState.refresh is LoadState.Error
                        || loadState.append is LoadState.Error -> {
                    ErrorView(
                        message = stringResource(id = R.string.error_message),
                        onRetry = retry
                    )
                }

                loadState.refresh is LoadState.NotLoading -> {

                }
            }
        }
    }


}


@Composable
fun BannerProduct(products: List<Product>) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = products.firstOrNull()?.image,
            contentDescription = products.firstOrNull()?.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(bottom = 20.dp)
        )

        Text(
            text = products.firstOrNull()?.title ?: "",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun HorizontalFreeScrollProducts(products: List<Product>) {
    LazyRow(
        modifier = Modifier.padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.size) { index ->
            Column(modifier = Modifier.width(124.dp)) {

                AsyncImage(
                    model = products[index].image ?: "-",
                    contentDescription = products[index].title,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(124.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = products[index].title ?: "",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SplitBannerProducts(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .height(240.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(products.size) { index ->
            Box(modifier = Modifier.fillMaxWidth()) {

                AsyncImage(
                    model = products[index].image,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Text(
                    text = products[index].title ?: "",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


