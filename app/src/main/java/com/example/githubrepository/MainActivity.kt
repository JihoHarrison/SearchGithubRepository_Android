package com.example.githubrepository

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import com.example.githubrepository.data.database.DatabaseProvider
import com.example.githubrepository.data.entity.GithubOwner
import com.example.githubrepository.data.entity.GithubRepoEntity
import com.example.githubrepository.databinding.ActivityMainBinding
import com.example.githubrepository.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    val job = Job()

    private lateinit var binding: ActivityMainBinding

    private val repositoryDao by lazy {
        DatabaseProvider.provideDB(applicationContext).repositoryDao()
    }
    private lateinit var adapter: RepositoryRecyclerAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()

//        launch {
//            addMockData()
//            val githubRepositories = loadGithubRepositories()
//            withContext(coroutineContext){
//                Log.e("repositories", githubRepositories.toString())
//            }
//        }
    }

    private fun initAdapter() {
        adapter = RepositoryRecyclerAdapter()
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        searchButton.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, SearchActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        launch(coroutineContext) {
            loadLikedRepositoryList()
        }
    }

    private suspend fun loadLikedRepositoryList() = withContext(Dispatchers.IO) {
        val repoList = repositoryDao.getHistory()
        withContext(Dispatchers.Main) {
            setData(repoList)
        }
    }

    private fun setData(githubRepositoryList: List<GithubRepoEntity>) = with(binding) {
        if (githubRepositoryList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setRepositoryList(githubRepositoryList){
                startActivity(
                    Intent(this@MainActivity, RepositoryActivity::class.java).apply {
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                    }
                )
            }
        }
    }

//    private suspend fun addMockData() = withContext(Dispatchers.IO) {
//        val mockData = (0 until 10).map {
//            // it -> int 0~9
//            GithubRepoEntity(
//                name = "repo $it",
//                fullName = "name $it",
//                owner = GithubOwner(
//                    "login",
//                    "avatar"
//                ),
//                description = null,
//                language = null,
//                updatedAt = Date().toString(),
//                stargazersCount = it
//            )
//        }
//        repositoryDao.insertAll(mockData)
//    }

//    private suspend fun loadGithubRepositories() = withContext(Dispatchers.IO){
//        val repositories = repositoryDao.getHistory()
//        return@withContext repositories
//    }
}