# Fake Store Products App with Ktor, Coil, and Koin in Compose Multiplatform (KMP)

This is a Kotlin Multiplatform Mobile (KMM) project that fetches products from the Fake Store API and displays them in a list. The app uses Ktor for API calls, Coil for image loading, and follows the MVVM architecture with Koin for dependency injection.

## Features
- Fetch products from [Fake Store API](https://fakeapi.platzi.com/)
- Display products in a list on the home screen
- Load product images using Coil
- Clean MVVM architecture
- Dependency injection with Koin
- Shared code between Android and iOS using KMM

## Tech Stack
- **Kotlin Multiplatform Mobile (KMM)**: Share business logic between Android and iOS
- **Ktor**: HTTP client for API communication
- **Coil**: Image loading for Android
- **Koin**: Dependency injection
- **MVVM Architecture**: Clean and scalable code structure

## Demo
## Android
https://github.com/user-attachments/assets/0850a82d-37d4-41e8-893c-a670bd1deec3

## IOS
https://github.com/user-attachments/assets/fbfbfbdb-2ba9-4826-aa22-5721b9976446

## Desktop
https://github.com/user-attachments/assets/45cbfbf5-edb5-4ad8-8441-dcbde2bc753c

## Getting Started

## createHttpClient
```kotlin
fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }
        defaultRequest {
            header("Content-Type", "application/json")
            url(Constant.BASE_URL)
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}
```

### API Implementation
```kotlin
class ApiImplementation : APIInterface {
    override suspend fun getProductList(productDTO: ProductDTO): Result<List<Product>, NetworkError> {
        val response = try {
            productDTO.httpClient.get(
                urlString = "products/"
            ) {
                parameter("offset", productDTO.offset)
                parameter("limit", productDTO.limit)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val productResponse = response.body<List<Product>>()
                Result.Success(productResponse)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}
```

### Prerequisites
- Android Studio Electric Eel or later
- Xcode 14 or later (for iOS development)
- Kotlin 1.9 or later

 ## 🤝 Want to Contribute?
All contributions are welcomed. This project is still in development. If you encounter any problems, please create an issue [here](https://github.com/waqas028/Ktor-Koin-KMP/issues) & if you want to contribute to this project, PRs are welcomed! 🙂

## 👨‍💻 Developed By

<a href="https://twitter.com/Shahzad_Ansari3" target="_blank">
  <img src="https://github.com/user-attachments/assets/99199ad1-a69d-4ceb-8599-e495a1ed937b" width="70" align="left">
</a>

**Muhammad Waqas**

[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/muhammad-waqas-4399361a3)
