package kevin.exam.github.utillity

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/08/29
 * Time: 5:35 오후
 */
class AuthTokenProvider(private val context: Context) {

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
    }

    fun updateToken(token: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putString(KEY_AUTH_TOKEN, token)
            .apply()
    }

    val token: String?
        get() = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(KEY_AUTH_TOKEN, null)

}