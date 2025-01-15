package org.waqas028.ktor_kmp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.waqas028.ktor_kmp.data.dto.ProductDTO
import org.waqas028.ktor_kmp.data.repo.GenericPagingRepository
import org.waqas028.ktor_kmp.data.repo.HomeRepo
import org.waqas028.ktor_kmp.data.response.Product
import org.waqas028.ktor_kmp.data.util.onError
import org.waqas028.ktor_kmp.data.util.onSuccess

class HomeVM(
    private val homeRepo: HomeRepo,
    private val genericPagingRepository: GenericPagingRepository,
) : ViewModel() {
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf("")

    private val _productResponse = MutableStateFlow<List<Product>?>(null)
    private val userRecentTaskUpdater = MutableStateFlow<ProductDTO?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    var productList = userRecentTaskUpdater.filterNotNull().flatMapLatest { productDTO ->
        genericPagingRepository.getPagingData(
            requestData = productDTO,
            updateRequest = { request, page -> request.copy(offset = page) },
            fetchData = { request ->
                homeRepo.getProductList(request)
                    .onSuccess {
                        isLoading = false
                        _productResponse.value = it
                    }.onError {
                        isLoading = false
                        error = it.name
                    }
                _productResponse.value.orEmpty()
            }
        )
    }.cachedIn(viewModelScope)
    fun getProductList(productDTO: ProductDTO) = viewModelScope.launch {
        userRecentTaskUpdater.update { productDTO }
    }
}