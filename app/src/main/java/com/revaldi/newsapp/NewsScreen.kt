package com.revaldi.newsapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.revaldi.newsapp.Data.Article
import com.revaldi.newsapp.Navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NewsScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var newsState by remember { mutableStateOf<List<Article>?>(null) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }


    LaunchedEffect(true) {
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                newsApiService.getHealthHeadlines()
            }
            newsState = response.articles
        }
    }

    Column {
        Text(
            text = "Top Health Headlines",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )
        if (newsState != null) {
            LazyColumn {
                items(newsState!!) { article ->
                    NewsListItem(article) {
                        selectedArticle = article
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "article",
                            value = selectedArticle
                        )
                        navController.navigate(Screen.Detail.route)
                    }
                }
            }
        } else {
            Text(text = "Loading...", modifier = Modifier.padding(16.dp))
        }
    }


}

@Composable
fun NewsListItem(article: Article, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onItemClick),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = rememberImagePainter(article.urlToImage),
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold
                )


            }

    }
}
