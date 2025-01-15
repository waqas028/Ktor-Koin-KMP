package org.waqas028.ktor_kmp.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.waqas028.ktor_kmp.data.networking.paging_source.GenericPagingDataSource

class GenericPagingRepository{
    private val _errorState = MutableSharedFlow<String>()
    val errorState: SharedFlow<String> = _errorState.asSharedFlow()

    fun <T : Any, R> getPagingData(
        requestData: R,
        updateRequest: (R, Int) -> R,
        fetchData: suspend (R) -> List<T>
    ): Flow<PagingData<T>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2,
            initialLoadSize = 10
        ),
        pagingSourceFactory = {
            GenericPagingDataSource(
                apiService = fetchData,
                requestData = requestData,
                updateRequest = updateRequest,
                errorState = _errorState
            )
        }
    ).flow
}