package co.jacobcloldev.apps.pruebadeingreso.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.jacobcloldev.apps.pruebadeingreso.R
import co.jacobcloldev.apps.pruebadeingreso.core.BaseViewHolder
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.databinding.UserListItemBinding
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(
    private val usersList: ArrayList<UserEntity>,
    private val itemClickListener: OnUserClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>(), Filterable {

    var userFilterList = ArrayList<UserEntity>()

    init {
        userFilterList = usersList
    }

    private lateinit var context: Context

    interface OnUserClickListener {
        fun onUserClick(user: UserEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
       context = parent.context
        val itemBinding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(userFilterList[position], position)
        }
    }

    override fun getItemCount() = userFilterList.size

    inner class MainViewHolder(private val binding: UserListItemBinding) :
        BaseViewHolder<UserEntity>(binding.root) {
        override fun bind(item: UserEntity, position: Int): Unit = with(binding) {
            name.text = item.name
            email.text = item.email
            phone.text = item.phone
            btnViewPost.setOnClickListener {
                itemClickListener.onUserClick(item)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charSearch = constraint.toString()
                userFilterList = if (charSearch.isEmpty()) {
                    usersList
                } else {
                    val resultList = ArrayList<UserEntity>()
                    for (row in usersList) {
                        if (row.name != null) {
                            if (row.name.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT))
                            ) {
                                resultList.add(row)
                            }
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userFilterList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userFilterList = results?.values as ArrayList<UserEntity>
                if (userFilterList.isEmpty()){
                    Toast.makeText(context, context.getString(R.string.list_is_empty), Toast.LENGTH_SHORT).show()
                }
                notifyDataSetChanged()
            }
        }
    }

}