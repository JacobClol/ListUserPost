package co.jacobcloldev.apps.pruebadeingreso.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.jacobcloldev.apps.pruebadeingreso.domain.Repo

class VMFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}