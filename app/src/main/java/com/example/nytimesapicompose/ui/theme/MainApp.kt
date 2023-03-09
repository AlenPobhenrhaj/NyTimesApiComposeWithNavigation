package com.example.nytimesapicompose.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.nytimesapicompose.data.Books
import com.example.nytimesapicompose.model.BooksApi
import com.example.nytimesapicompose.model.BooksApiService
import com.example.nytimesapicompose.widgets.DisplayBookComposable
import kotlinx.coroutines.MainScope
import retrofit2.Response


@Composable
fun Screen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "List of Books") },
            )
        }
    ) {
        Column {
            val service = BooksApi()
            val mainScope = MainScope()
            BookList(service, navController)
        }
    }
}



@Composable
fun BookList(service: BooksApiService, navController: NavHostController) {
    var response by remember { mutableStateOf<Resource<Books>>(Resource.Loading()) }

    LaunchedEffect(Unit) {
        response = service.getBestSellers().toResource()

    }

    when (val result = response) {
        is Resource.Loading -> {
            Text("Loading books...")
        }
        is Resource.Success -> {
            val books = result.data.results
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(books) { book ->
                    for (bookDetail in book.book_details) {
                        val onClick: () -> Unit = {

                            navController.navigate("bookDetailsPage/${bookDetail.title}/${bookDetail.description}")
                        }
                        DisplayBookComposable(bookDetail.title, bookDetail.author, onClick)
                    }
                    Divider()
                }
            }
        }
        is Resource.Failure -> {
            Text("Failed to load books: ${result.throwable.message}")
        }
    }
}


sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val throwable: Throwable) : Resource<Nothing>()
}

fun <T> Response<T>.toResource(): Resource<T> {
    return if (isSuccessful && body() != null) {
        Resource.Success(body()!!)
    } else {
        Resource.Failure(Throwable(message()))

    }
}