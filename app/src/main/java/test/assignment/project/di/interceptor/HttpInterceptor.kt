package test.assignment.project.di.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import test.assignment.project.BuildConfig
import javax.inject.Inject

class HttpInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("X-Api-Key", BuildConfig.API_KEY)
            .build()

        val response = chain.proceed(request)
        Log.d("HttpInterceptor", "Response code: ${response.code}")
        Log.d("HttpInterceptor", "Response body: ${response.peekBody(Long.MAX_VALUE).string()}")

        return response
    }
}