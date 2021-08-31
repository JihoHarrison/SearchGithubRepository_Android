package com.example.githubrepository.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepository.data.entity.GithubRepoEntity

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insert(repo: GithubRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repo: List<GithubRepoEntity>)

    @Query("SELECT * FROM githubrepository")
    suspend fun getHistory() : List<GithubRepoEntity>

    @Query("SELECT * FROM githubrepository WHERE fullName=:fullname")
    suspend fun getRepository(fullname: String):GithubRepoEntity

    @Query("DELETE FROM githubrepository WHERE fullName=:fullname")
    suspend fun remove(fullname: String)

    @Query("DELETE FROM githubrepository")
    suspend fun clearAll()

}