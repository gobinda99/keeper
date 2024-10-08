package com.gobinda.assignment.keeper.ui.home

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClicked: (Product) -> Unit
) {
    val sectionLazyPagingItems = viewModel.secProducts.collectAsLazyPagingItems()

    HomeContent(modifier, sectionLazyPagingItems, onProductClicked) {
        viewModel.onRetry()
    }
}


@Composable
private fun HomeContent(
    modifier: Modifier,
    secLazyPagingItems: LazyPagingItems<Section>,
    onProductClicked: (Product) -> Unit,
    retry: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TitleTopBar(title = stringResource(id = R.string.home))
    }) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            items(secLazyPagingItems.itemCount, key = {secLazyPagingItems[it].hashCode()}) {
                secLazyPagingItems[it]?.apply {
                    when (sectionType) {
                        SectionType.BANNER -> BannerProduct(products)
                        SectionType.HORIZONTAL_FREE_SCROLL -> HorizontalFreeScrollProducts(
                            products, onProductClicked
                        )
                        SectionType.SPLIT_BANNER -> {
                            SplitBannerProducts(products, onProductClicked)
                        }
                        else -> {}
                    }
                }

            }
        }
        secLazyPagingItems.apply {
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
                .padding(bottom = 24.dp)
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
fun HorizontalFreeScrollProducts(products: List<Product>, onProductClicked: (Product) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.size, key = {products[it].hashCode()}) { index ->
            Column(modifier = Modifier
                .width(124.dp)
                .clickable {
                    onProductClicked.invoke(products[index])
                }) {

                AsyncImage(
                    model = products[index].image ?: "-",
                    contentDescription = products[index].title,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(124.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = products[index].title ?: "",
                    fontWeight = FontWeight.Bold,
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
fun SplitBannerProducts(products: List<Product>, onProductClicked: (Product) -> Unit) {
    val size = remember {
        if(products.size > 2) 2 else products.size
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(bottom = 24.dp)
            .height(240.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(size, key = {products[it].hashCode()}) { index ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { onProductClicked.invoke(products[index]) }) {

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


