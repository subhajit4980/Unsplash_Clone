package com.example.unplashclone.screens.common


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.unplashclone.R
import com.example.unplashclone.download.AndroidDownloader
import com.example.unplashclone.model.UnsplashImage
import com.example.unplashclone.model.Urls
import com.example.unplashclone.model.User
import com.example.unplashclone.model.UserLinks
import com.example.unplashclone.ui.theme.HeartRed
import com.example.unplashclone.utils.Common.saveFile
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalCoilApi
@Composable
fun ListContent(items: LazyPagingItems<UnsplashImage>,context: Context) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id },
//            key = { UUID.randomUUID() },
            contentType = items.itemContentType { "contentType" }) { index ->
            val item = items[index]
            item?.let { Image_Item(it,context) }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalCoilApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Image_Item(unsplashImage: UnsplashImage,context:Context) {
    val painter = rememberImagePainter(data = unsplashImage.urls.regular,
        builder = {
            crossfade(durationMillis = 1000)
            error(R.drawable.ic_placeholder)
            placeholder(R.drawable.ic_placeholder).scale(scale = Scale.FILL)
        }
    )
    val scaleModifier = Modifier
        .fillMaxSize()
        .graphicsLayer(scaleX = 1.5f, scaleY = 1.5f)
    Card(
        modifier = Modifier
            .clickable {
            }
            .height(300.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        onClick = {}
    ) {
        Column(modifier = Modifier.weight(10f)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(8f),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize().then(scaleModifier),
                    painter = painter,
                    contentDescription = "Unsplash Image",
                    contentScale = ContentScale.Crop,

                    )
//
                FloatingActionButton(
                    modifier = Modifier.width(40.dp).height(40.dp).align(Alignment.TopEnd),
                    onClick = {
                        Log.d("DownloadLink",unsplashImage.links.download.toString())
                        val link=unsplashImage.links.download.toString()
                        val name=unsplashImage.id.toString()+".jpg"
                        try {
                            val downloader=AndroidDownloader(context)
                            downloader.downloadFile(link,name,"Unsplash/")
//                            saveFile("Unsplash",link,name)
                        }catch (e:Exception){
                            Log.d("DownloadLink_ERROR",e.toString())
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = "add icon")
                }
            }

            Row(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(all = 0.dp)
                    .padding(horizontal = 0.dp)
                    .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Photo by ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(unsplashImage.user.username)
                        }
                        append(" on Unsplash")
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .clickable {
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://unsplash.com/@${unsplashImage.user.username}?utm_source=DemoApp&utm_medium=referral")
                            )
                            startActivity(context, browserIntent, null)
                        }
                        .padding(start = 6.dp)
                )
                LikeCounter(
                    modifier = Modifier
                        .weight(3f)
                        .padding(end = 6.dp),
                    painter = painterResource(id = R.drawable.ic_heart),
                    likes = "${unsplashImage.likes}"
                )
            }
        }

    }
    }

@Composable
fun LikeCounter(
    modifier: Modifier,
    painter: Painter,
    likes: String
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painter,
            contentDescription = "Heart Icon",
            tint = HeartRed
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = likes,
            color = Color.White,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
