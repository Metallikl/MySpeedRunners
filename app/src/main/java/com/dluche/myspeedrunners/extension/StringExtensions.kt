package com.dluche.myspeedrunners.extension

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val DATE_ONLY = "dd/MM/yyyy"
const val DATE_TIME_SIMPLE = "dd/MM/yyyy HH:mm"
const val YOUTUBE_HOST = "www.youtube.com"
const val YOUTU_BE_HOST = "youtu.be"
const val TWITCH_HOST = "www.twitch.tv"

fun String.formatToDate(
    dateFormatIn: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME,
    dateFormatOut: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_ONLY)
) = try {
    LocalDate.parse(this, dateFormatIn).format(dateFormatOut)
} catch (e: Exception) {
    ""
}

fun String?.isNotNullNorEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

@Composable
fun String?.RunWithNotNullNorEmpty(block: @Composable (String) -> Unit) {
    if (this.isNotNullNorEmpty()) {
        this?.let {
            block(it)
        }
    }
}

@Composable
fun String.asTwitchEmbeddedUrl(): String {
    return try {
        this.split("/").lastOrNull().let {
            "https://player.twitch.tv/?video=$it&parent=www.speedrun.com&autoplay=true"
        }
    } catch (e: Exception) {
        this
    }
}

fun String.extractTwitchVideoId(): String {
    return try {
        this.split("/").lastOrNull().orEmpty()
    } catch (e: Exception) {
        this
    }
}

fun String.isYoutubeUrl(): Boolean {
    return this.contains(YOUTUBE_HOST) || this.contains(YOUTU_BE_HOST)
}

fun String.isTwitchUrl(): Boolean {
    return this.contains(TWITCH_HOST)
}

fun String.openBrowser(context: Context) {
    val openURL = Intent(Intent.ACTION_VIEW).apply {
        data = this@openBrowser.toUri()
    }
    context.startActivity(openURL)
}

fun String.extractYoutubeVideoId(): String? {
    // Lista de expressões regulares para diferentes padrões de URL
    val regexList = listOf(
        // URLs com parâmetros ?v= ou ?vi=
        "[?&](?:v|vi)=([a-zA-Z0-9_-]{11})",
        // Encodes com /embed/, /v/, /vi/, /shorts/ , /live/
        "/(?:embed|v|vi|shorts|live)/([a-zA-Z0-9_-]{11})",
        // youtu.be URLs
        "youtu\\.be/([a-zA-Z0-9_-]{11})",
        // URLs com fragmento (#) no final
        "#.*?/([a-zA-Z0-9_-]{11})"
    )

    for (regex in regexList) {
        val match = Regex(regex).find(this)
        if (match != null) return match.groupValues[1]
    }

    return null
}

//fun main() {
//    val urls = listOf(
//        "https://youtube.com/shorts/dQw4w9WgXcQ?feature=share",
//        "//www.youtube-nocookie.com/embed/up_lNV-yoK4?rel=0",
//        "http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo",
//        "http://www.youtube.com/watch?v=cKZDdG9FTKY&feature=channel",
//        "http://www.youtube.com/watch?v=yZ-K7nCVnBI&playnext_from=TL&videos=osPknwzXEas&feature=sub",
//        "http://www.youtube.com/ytscreeningroom?v=NRHVzbJVx8I",
//        "http://www.youtube.com/user/SilkRoadTheatre#p/a/u/2/6dwqZw0j_jY",
//        "http://youtu.be/6dwqZw0j_jY",
//        "http://www.youtube.com/watch?v=6dwqZw0j_jY&feature=youtu.be",
//        "http://youtu.be/afa-5HQHiAs",
//        "http://www.youtube.com/user/Scobleizer#p/u/1/1p3vcRhsYGo?rel=0",
//        "http://www.youtube.com/embed/nas1rJpm7wY?rel=0",
//        "http://www.youtube.com/watch?v=peFZbP64dsU",
//        "http://youtube.com/v/dQw4w9WgXcQ?feature=youtube_gdata_player",
//        "http://youtube.com/vi/dQw4w9WgXcQ?feature=youtube_gdata_player",
//        "http://youtube.com/?v=dQw4w9WgXcQ&feature=youtube_gdata_player",
//        "http://youtube.com/?vi=dQw4w9WgXcQ&feature=youtube_gdata_player",
//        "http://youtube.com/watch?v=dQw4w9WgXcQ&feature=youtube_gdata_player",
//        "http://youtube.com/watch?vi=dQw4w9WgXcQ&feature=youtube_gdata_player",
//        "http://youtu.be/dQw4w9WgXcQ?feature=youtube_gdata_player"
//    )
//
//    for (url in urls) {
//        println("URL: $url")
//        println("Video ID: ${extractYoutubeVideoId(url)}\n")
//    }
//}
