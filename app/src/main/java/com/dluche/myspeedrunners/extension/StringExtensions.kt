package com.dluche.myspeedrunners.extension

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import com.airbnb.lottie.animation.content.Content
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType
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
fun String.urlToEmbedded(): String {
    return when (this.toUri().host) {
        YOUTUBE_HOST.lowercase() -> {
            try {
                this.split("https://").let {
                    if (it.size == 1) {
                        this.replace("watch?v=", "embed/")
                    } else if (it.size > 1) {
                        "https://" + it[1].replace("watch?v=", "embed/")
                    } else {
                        this
                    }
                }
            } catch (e: Exception) {
                this
            }
        }

        YOUTU_BE_HOST -> {
            try {
                this.split("https://").let {
                    if (it.size == 1) {
                        this.replace(YOUTU_BE_HOST, "$YOUTUBE_HOST/embed/")
                    } else if (it.size > 1) {
                      "https://" + it[1].replace(YOUTU_BE_HOST, "$YOUTUBE_HOST/embed/")
                    } else {
                        this
                    }
                }
            } catch (e: Exception) {
                this
            }
        }

        TWITCH_HOST.lowercase() -> {
            try {
                this.split("/").lastOrNull().let {
                    //return "https://player.twitch.tv/?parent=com.dluche.myspeedrunners&video=$it"
                    return "https://player.twitch.tv/?video=$it&parent=www.speedrun.com&autoplay=true"
                }
            } catch (e: Exception) {
                this
            }
        }

        else -> {
            this
        }
    }
}

fun String.openBrowser(context: Context) {
    val openURL = Intent(Intent.ACTION_VIEW).apply {
        data = this@openBrowser.toUri()
    }
    context.startActivity(openURL)
}