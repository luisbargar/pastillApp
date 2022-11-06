package es.codigonline.proyecto.smarthome.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import es.codigonline.proyecto.smarthome.R
import es.codigonline.proyecto.smarthome.app.App
import es.codigonline.proyecto.smarthome.databinding.ActivityHomeBinding
import es.codigonline.proyecto.smarthome.databinding.NavHeaderHomeBinding
import es.codigonline.proyecto.smarthome.ui.newMedication.NewMedicationActivity
import es.codigonline.proyecto.smarthome.ui.sign.LoginActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_favs, R.id.nav_personales
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()

        binding.appBarHome.fab.setOnClickListener {
            startActivity(Intent(this, NewMedicationActivity::class.java))
        }

        val navViewBinding = NavHeaderHomeBinding.bind(binding.navView.getHeaderView(0))
        navViewBinding.usuario.text = App.getUsuario()!!.nombre

        navViewBinding.logout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            App.clear()
            finish()
        }
    }
}