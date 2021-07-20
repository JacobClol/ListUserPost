package co.jacobcloldev.apps.pruebadeingreso.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.jacobcloldev.apps.pruebadeingreso.core.BaseViewHolder
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.databinding.PostListItemBinding
import co.jacobcloldev.apps.pruebadeingreso.databinding.UserListItemBinding

class PostAdapter(private val postList: ArrayList<PostEntity>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(postList[position], position)
        }
    }

    override fun getItemCount() = postList.size

    inner class MainViewHolder(private val binding: PostListItemBinding) :
        BaseViewHolder<PostEntity>(binding.root) {
        override fun bind(item: PostEntity, position: Int): Unit = with(binding) {
            title.text = item.title
            body.text = item.body
        }
    }

}