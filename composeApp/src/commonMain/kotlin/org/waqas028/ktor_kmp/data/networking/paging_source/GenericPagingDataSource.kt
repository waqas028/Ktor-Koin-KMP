package org.waqas028.ktor_kmp.data.networking.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.io.IOException

class GenericPagingDataSource<T : Any, R>(
    private val apiService: suspend (R) -> List<T>,
    private val requestData: R,
    private val updateRequest: (R, Int) -> R,
    private val errorState: MutableSharedFlow<String>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1
            val updatedRequestData = updateRequest(requestData, nextPage)
            val responseData = apiService(updatedRequestData)
            LoadResult.Page(
                data = responseData,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (responseData.isNotEmpty()) nextPage + 1 else null
            )
        } catch (exception: HttpException) {
            errorState.emit("Network error: ${exception.message}")
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            errorState.emit("Network error: ${exception.message}")
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            errorState.emit("An unexpected error occurred: ${exception.message}")
            LoadResult.Error(exception)
        }
    }
}