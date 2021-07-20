package co.jacobcloldev.apps.pruebadeingreso.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import co.jacobcloldev.apps.pruebadeingreso.R
import co.jacobcloldev.apps.pruebadeingreso.core.Resource
import co.jacobcloldev.apps.pruebadeingreso.core.VMFactory
import co.jacobcloldev.apps.pruebadeingreso.data.Services
import co.jacobcloldev.apps.pruebadeingreso.data.db.AppDataBase
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserModel
import co.jacobcloldev.apps.pruebadeingreso.databinding.ActivityMainBinding
import co.jacobcloldev.apps.pruebadeingreso.domain.ImplementRepo
import co.jacobcloldev.apps.pruebadeingreso.ui.view.adapter.UserAdapter
import co.jacobcloldev.apps.pruebadeingreso.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private val viewModel by viewModels<MainViewModel> {
        VMFactory(
            ImplementRepo(
                Services(
                    AppDataBase.getDataBase(applicationContext)
                )
            )
        )
    }

    private lateinit var binding: ActivityMainBinding

    private var listUsers: ArrayList<UserEntity> = ArrayList()

    private val userAdapter by lazy {
        UserAdapter(listUsers, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        setupObserver()
        binding.textInputLayoutSearch.onActionViewExpanded()
        setUpSearchView()
    }

    override fun onUserClick(user: UserEntity) {

        val bundle = Bundle()
        bundle.putParcelable("user", user)

        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("userData", bundle)
        }
        startActivity(intent)
    }


    private fun setupRecyclerView() {
        binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchResults.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewSearchResults.adapter = userAdapter
    }

    private fun setUpSearchView() {
        binding.textInputLayoutSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun setupObserver() {
        viewModel.getUserDB.observe(this, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val userList = result.data as List<UserEntity>
                    if (userList.isNotEmpty()) {
                        listUsers.clear()
                        listUsers.addAll(userList)
                        userAdapter.notifyDataSetChanged()
                    } else {
                        getUserFromApi()
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error loading users ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun getUserFromApi() {
        viewModel.fetchUsers.observe(this, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val userList = result.data as List<UserModel>
                    val userEntityList = userList.map {
                        UserEntity(it.id, it.name, it.email, it.phone)
                    }
                    if (userEntityList.isNotEmpty()) {
                        listUsers.clear()
                        listUsers.addAll(userEntityList)
                        userAdapter.notifyDataSetChanged()
                        insertUserDB(userEntityList)
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error loading users ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun insertUserDB(userEntityList: List<UserEntity>) {
        viewModel.insertUserDB(userEntityList)
        Toast.makeText(this, "The users save un local data base.", Toast.LENGTH_SHORT).show()
    }

}