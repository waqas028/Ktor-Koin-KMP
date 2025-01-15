package org.waqas028.ktor_kmp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import io.ktor.client.HttpClient
import ktor_kmp.composeapp.generated.resources.Res
import ktor_kmp.composeapp.generated.resources.image_placeholder
import ktor_kmp.composeapp.generated.resources.ktor_with_pagination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.waqas028.ktor_kmp.data.dto.ProductDTO
import org.waqas028.ktor_kmp.data.response.Product
import org.waqas028.ktor_kmp.presentation.component.CustomCircularProgressBar
import org.waqas028.ktor_kmp.presentation.component.LinearProgress

@Composable
fun HomeScreen(homeVM: HomeVM, client: HttpClient){
    val error = homeVM.error
    val loading = homeVM.isLoading
    val productList = homeVM.productList.collectAsLazyPagingItems()

    LaunchedEffect(Unit){
        homeVM.getProductList(
            productDTO = ProductDTO(
                httpClient = client,
                offset = 1,
                limit = 10
            )
        )
    }

    HomeScreen(
        error = error,
        isLoading = loading,
        productList = productList
    )
}

@Composable
private fun HomeScreen(
    error: String,
    isLoading: Boolean,
    productList: LazyPagingItems<Product>?
) {
    val state = rememberLazyGridState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.ktor_with_pagination),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            if (error.isNotEmpty()) Text(
                text = error,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            if(productList?.loadState?.refresh is LoadState.Loading || productList?.loadState?.append is LoadState.Loading) LinearProgress(modifier = Modifier.padding(vertical = 5.dp))
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = state,
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(productList?.itemCount ?: 0) { item ->
                    productList?.get(item)?.let { product ->
                        ProductCard(modifier = Modifier, product = product)
                    }
                }
            }
        }

        if (isLoading) CustomCircularProgressBar()
    }
}

@Composable
private fun ProductCard(modifier: Modifier, product: Product) {
    Column(modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(12.dp))) {
        AsyncImage(
            model = product.images.getOrNull(0),
            contentDescription = "",
            placeholder = painterResource(Res.drawable.image_placeholder),
            error = painterResource(Res.drawable.image_placeholder),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.CenterHorizontally)
                .aspectRatio(1f)
        )
        Text(
            text = "$${product.price}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )
        Text(
            text = product.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            maxLines = 2,
            lineHeight = 20.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .heightIn(min = 70.dp)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        )
    }
}