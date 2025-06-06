import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun getOpenHours(url: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(url).get()
            val operatingHoursElement = doc.selectFirst("div.H3ua4")
            val text = operatingHoursElement?.text()
            Log.d("getOpenHours", "[$url] → operatingHoursElement text = $text")
            text
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("getOpenHours", "크롤링 에러: ${e.message}")
            null
        }
    }
}
