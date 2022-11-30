package es.luisbarreiros.proyecto.pastillApp.ui

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
import es.luisbarreiros.proyecto.pastillApp.R
import es.luisbarreiros.proyecto.pastillApp.app.App
import es.luisbarreiros.proyecto.pastillApp.databinding.ActivityHomeBinding
import es.luisbarreiros.proyecto.pastillApp.databinding.NavHeaderHomeBinding
import es.luisbarreiros.proyecto.pastillApp.ui.newMedication.NewMedicationActivity
import es.luisbarreiros.proyecto.pastillApp.ui.sign.LoginActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater) //se crea el binding
        setContentView(binding.root) //se pone la lista
        setSupportActionBar(binding.appBarHome.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout //creación de drawer layout
        val navView: NavigationView = binding.navView //creación de navView
        val navController = findNavController(R.id.nav_host_fragment_content_home) //creación del NavController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_tomas, R.id.nav_stocks //estos id, están dentro del navigation.
            ), drawerLayout //lo metemos dentro del drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration) //vinculamos navController con el appBarConfiguration
        navView.setupWithNavController(navController) //el navView funciona con el navController
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
               return true
    }
    override fun onSupportNavigateUp(): Boolean { //da el soporte de navegacion para los fragmentos.
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onStart() { //sobreescribimos onStart()
        super.onStart()

        binding.appBarHome.fab.setOnClickListener { //funcionamiento del botón "+"
            startActivity(Intent(this, NewMedicationActivity::class.java))
        }
        val navViewBinding = NavHeaderHomeBinding.bind(binding.navView.getHeaderView(0))
        navViewBinding.usuario.text = App.getUsuario()!!.nombre //aparece el nombre del usuario logueado en la caja de texto

        navViewBinding.logout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            App.clear() //botón para finalizar sesión.
            finish()
        }
    }
}