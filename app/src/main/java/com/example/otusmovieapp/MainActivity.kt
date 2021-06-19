package com.example.otusmovieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.otusmovieapp.MoviesFragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        if (savedInstanceState == null) openMoviesPage()
    }

    private fun initViews() {
        bottomNavigationView = findViewById(R.id.navigator)
        bottomNavigationView.setOnNavigationItemReselectedListener { }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.page_movies -> openMoviesPage()
                R.id.page_favorites -> openFavoritesPage()
            }
            true
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
        else
            ExitDialog().show(supportFragmentManager, "exitDialog")
    }

    private fun openMoviesPage() {
        val fragment = MoviesFragment.newInstance()
        openFragment(fragment)
    }

    private fun openFavoritesPage() {
        val fragment = FavoritesFragment.newInstance()
        openFragment(fragment)
    }

    private fun openMovieDetailsPage(movie: Movie){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(movie))
            .addToBackStack(null)
            .commit()
    }

    private fun openFragment(fragment: Fragment){
        if (supportFragmentManager.backStackEntryCount > 0) {
            val id = supportFragmentManager.getBackStackEntryAt(0).id
            supportFragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onDetailsSelected(movie: Movie) {
        movie.isReviewed = true
        openMovieDetailsPage(movie)
    }
}