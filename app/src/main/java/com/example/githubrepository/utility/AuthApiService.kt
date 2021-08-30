package kevin.exam.github.utillity


import kevin.exam.github.data.response.GithubAccessTokenResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Response<GithubAccessTokenResponse>
}