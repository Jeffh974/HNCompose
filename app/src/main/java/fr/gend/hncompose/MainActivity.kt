package fr.gend.hncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.gend.hncompose.data.HNItem
import fr.gend.hncompose.data.HackerNewsAPI
import fr.gend.hncompose.ui.theme.HNComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = Retrofit.Builder()
            .baseUrl("https://hacker-news.firebaseio.com/v0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HackerNewsAPI::class.java)

        setContent {

            HNComposeTheme {
                Screen(api)
            }
        }
    }
}

@Composable
fun Screen(api: HackerNewsAPI?) {

    var list: List<Int> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(null) {
        if (api == null) return@LaunchedEffect
        list = api.topStories()
    }

    ItemList(list, api)
}


@Composable
fun ItemList(list: List<Int>, api: HackerNewsAPI?) {
    Column(
        modifier = Modifier.fillMaxSize() ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (list.isEmpty()) {
            CircularProgressIndicator(Modifier.size(100.dp))
        } else {
            LazyColumn(
                Modifier.fillMaxSize()
            ) {
                items(list) {itemId ->
                    var item: HNItem? by remember {
                        mutableStateOf(null)
                    }

                    LaunchedEffect(null) {
                        if(api == null) return@LaunchedEffect
                        item = api.item(itemId)
                    }

                    Row(
                        Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = item?.title ?: "...",
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    HNComposeTheme {
        Screen(null)
    }
}