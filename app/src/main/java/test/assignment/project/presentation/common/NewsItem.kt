package test.assignment.project.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import test.assignment.project.data.local.entities.NewsEntity

@Composable
fun NewsItem(
    news: NewsEntity,
    onClick: (String) -> Unit,
    showDivider: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth().clickable { onClick(news.url) }
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {
        Text(
            text = news.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
        news.description?.let { description ->
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
        }
        news.urlToImage?.let { imageUrl ->
            BaseImage(
                model = imageUrl,
                contentDescription = news.title,
                modifier = Modifier
            )
        }
        news.author?.let { author ->
            Text(
                text = author,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)
            )
        }
        if(showDivider) {
            HorizontalDivider()
        }
    }
}