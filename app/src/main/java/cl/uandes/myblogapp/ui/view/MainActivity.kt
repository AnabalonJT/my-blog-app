package cl.uandes.myblogapp.ui.view
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.setupActionBarWithNavController
import cl.uandes.myblogapp.R
import cl.uandes.myblogapp.databinding.ActivityMainBinding
import cl.uandes.myblogapp.services.location.ForegroundLocationService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost: NavHost = (
            supportFragmentManager.findFragmentById(R.id.nav_host_activity_main)
            ) as NavHost
        navigationController = navHost.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.posts_navigation, R.id.signin_navigation)
        )

        setupActionBarWithNavController(navigationController, appBarConfiguration)

        navigationController.navigate(R.id.signin_navigation)

        ForegroundLocationService.askPermissionsIfNotGranted(this)
    }
}
