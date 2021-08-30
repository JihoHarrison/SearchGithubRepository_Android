package com.example.githubrepository.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepository.data.entity.GithubRepoEntity
import com.example.githubrepository.data.extensions.loadCenterInside
import com.example.githubrepository.databinding.ViewholderRepositoryItemBinding

class RepositoryRecyclerAdapter :
    RecyclerView.Adapter<RepositoryRecyclerAdapter.RepositoryItemViewHolder>() {

    private var repositoryList: List<GithubRepoEntity> = listOf()
    private lateinit var repositoryClickListener: (GithubRepoEntity) -> Unit

    inner class RepositoryItemViewHolder(
        private val binding: ViewholderRepositoryItemBinding,
        val searchResultClickListener: (GithubRepoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        // data class 즉, entity와 각각 아이템의 뷰 들을 연결시켜주는 함수.
        fun bindData(data: GithubRepoEntity) = with(binding) {
            ownerProfileImageView.loadCenterInside(data.owner.avatarUrl, 24f)
            ownerNameTextView.text = data.owner.login
            nameTextView.text = data.fullName
            subtextTextView.text = data.description
            stargazersCountText.text = data.stargazersCount.toString()
            // 사용 언어에 대해서는 nullCheck 시도
            data.language?.let { language ->
                languageText.isGone = false
                languageText.text = language
            } ?: kotlin.run {
                languageText.isGone = true
                languageText.text = ""
            }
        }

        fun bindViews(data: GithubRepoEntity) {
            binding.root.setOnClickListener {
                searchResultClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val view = ViewholderRepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryItemViewHolder(view, repositoryClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bindViews(repositoryList[position])
        holder.bindData(repositoryList[position])
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    fun setRepositoryList(searchResultList: List<GithubRepoEntity>, searchResultClickListener: (GithubRepoEntity) -> Unit){
        this.repositoryList = searchResultList
        this.repositoryClickListener = searchResultClickListener
        notifyDataSetChanged()
    }
}