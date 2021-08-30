package com.example.githubrepository.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubrepository.data.dao.RepositoryDao
import com.example.githubrepository.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 4)
abstract class SimpleGithubDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}