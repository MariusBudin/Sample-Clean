package com.mariusbudin.sampleclean

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mariusbudin.sampleclean.core.extension.hide
import com.mariusbudin.sampleclean.core.extension.show
import com.mariusbudin.sampleclean.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getEpisodesFragment()?.let { showFragment(it) }

        val navigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.charactersFragment -> {
                        getCharactersFragment()?.let { showFragment(it) }
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.episodesFragment -> {
                        getEpisodesFragment()?.let { showFragment(it) }
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            navigationItemSelectedListener
        )
    }

    private fun getCharactersFragment() =
        Class.forName("com.mariusbudin.sampleclean.characters.presentation.CharactersFragment")
            .newInstance() as? Fragment

    private fun getEpisodesFragment() =
        Class.forName("com.mariusbudin.sampleclean.episodes.presentation.EpisodesFragment")
            .newInstance() as? Fragment

    private fun showFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun hideNavigation() {
        binding.bottomNavigationView.hide()
    }

    fun showNavigation() {
        binding.bottomNavigationView.show()
    }
}