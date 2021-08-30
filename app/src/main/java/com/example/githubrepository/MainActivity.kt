package com.example.githubrepository

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.githubrepository.data.database.DatabaseProvider
import com.example.githubrepository.data.entity.GithubOwner
import com.example.githubrepository.data.entity.GithubRepoEntity
import com.example.githubrepository.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    val job = Job()

    private lateinit var binding: ActivityMainBinding

    val repositoryDao by lazy { DatabaseProvider.provideDB(applicationContext).repositoryDao() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        launch {
            addMockData()
            val githubRepositories = loadGithubRepositories()
            withContext(coroutineContext){
                Log.e("repositories", githubRepositories.toString())
            }
        }
    }

    private fun initViews() = with(binding){
        searchButton.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, SearchActivity::class.java)
            )
        }

    }

    private suspend fun addMockData() = withContext(Dispatchers.IO) {
        val mockData = (0 until 10).map {
            // it -> int 0~9
            GithubRepoEntity(
                name = "repo $it",
                fullName = "name $it",
                owner = GithubOwner(
                    "login",
                    "avatar"
                ),
                description = null,
                language = null,
                updatedAt = Date().toString(),
                stargazersCount = it
            )
        }
        repositoryDao.insertAll(mockData)
    }

    private suspend fun loadGithubRepositories() = withContext(Dispatchers.IO){
        val repositories = repositoryDao.getHistory()
        return@withContext repositories
    }
}