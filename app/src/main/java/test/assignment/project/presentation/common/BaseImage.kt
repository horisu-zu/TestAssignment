package test.assignment.project.presentation.common

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun BaseImage(
    model: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .memoryCacheKey(model)
            .diskCacheKey(model)
            .listener(
                onSuccess = { _ , result ->
                    //Log.d("Image", "Image successfully loaded: $result")
                },
                onError = { _ , error ->
                    Log.d("Image", "Error loading image: $error")
                }
            )
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.fillMaxWidth().aspectRatio(16f / 9f),
        loading = {
            Box(modifier = Modifier.fillMaxSize())
        }
    )
}