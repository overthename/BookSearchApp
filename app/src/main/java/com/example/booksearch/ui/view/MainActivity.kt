package com.example.booksearch.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.booksearch.R
import com.example.booksearch.data.db.BookSearchDatabase
import com.example.booksearch.data.repository.BookSearchRepositoryImpl
import com.example.booksearch.databinding.ActivityMainBinding
import com.example.booksearch.ui.viewmodel.BookSearchViewModel
import com.example.booksearch.ui.viewmodel.BookSearchViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        setupBottomNavigationView()
//        //앱이 처음 실행되었을 때 searchfragment 실행
//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
//        }
        setupJetpackNavigation()

        val database = BookSearchDatabase.getInstance(this)
        val bookSearchRepository = BookSearchRepositoryImpl(database)
        val factory = BookSearchViewModelProviderFactory(bookSearchRepository, this)
        bookSearchViewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]

    }

    private fun setupJetpackNavigation() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.booksearch_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_search, R.id.fragment_favorite, R.id.fragment_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
//    private fun setupBottomNavigationView() {
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.fragment_search -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SearchFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_favorite -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, FavoriteFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_settings -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SettingsFragment())
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
//    }

}