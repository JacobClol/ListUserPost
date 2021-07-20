package co.jacobcloldev.apps.pruebadeingreso.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import co.jacobcloldev.apps.pruebadeingreso.R
import co.jacobcloldev.apps.pruebadeingreso.core.Resource
import co.jacobcloldev.apps.pruebadeingreso.core.VMFactory
import co.jacobcloldev.apps.pruebadeingreso.data.Services
import co.jacobcloldev.apps.pruebadeingreso.data.db.AppDataBase
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostModel
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserModel
import co.jacobcloldev.apps.pruebadeingreso.databinding.ActivityPostBinding
import co.jacobcloldev.apps.pruebadeingreso.domain.ImplementRepo
import co.jacobcloldev.apps.pruebadeingreso.ui.view.adapter.PostAdapter
import co.jacobcloldev.apps.pruebadeingreso.ui.viewmodel.MainViewModel

class PostActivity: AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(
            ImplementRepo(
                Services(
                    AppDataBase.getDataBase(applicationContext)
                )
            )
        )
    }

    private lateinit var binding: ActivityPostBinding

    private lateinit var user: UserEntity

    private var listPost: ArrayList<PostEntity> = ArrayList()

    private val postAdapter by lazy {
        PostAdapter(listPost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.getBundleExtra("userData")
        bundle?.let {
            user = it.getParcelable("user")!!
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val titleBar = getString(R.string.titlePostBar)
        title = titleBar
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        setupObserver()
        setDatauser()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewPostsResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPostsResults.adapter = postAdapter
    }

    private fun setDatauser(){
        binding.name.text = user.name
        binding.email.text = user.email
        binding.phone.text = user.phone
    }

    private fun setupObserver() {
        viewModel.getPostDB(user.id).observe(this, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val userList = result.data as List<PostEntity>
                    if (userList.isNotEmpty()) {
                        listPost.clear()
                        listPost.addAll(userList)
                        postAdapter.notifyDataSetChanged()
                    } else {
                        getPostFromApi()
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error loading movies ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun getPostFromApi() {
        viewModel.setUserId(user.id)
        viewModel.fetchPostByUser.observe(this, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val postList = result.data as List<PostModel>
                    val postEntityList = postList.map {
                        PostEntity(it.id, it.userId, it.title, it.body)
                    }
                    if (postEntityList.isNotEmpty()) {
                        listPost.clear()
                        listPost.addAll(postEntityList)
                        postAdapter.notifyDataSetChanged()
                        insetPostDataBase(postEntityList)
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error loading movies ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun insetPostDataBase(postEntityList: List<PostEntity>) {
        viewModel.insertPostDB(postEntityList)
    }

}