package com.mabrouk.sportingapp.persentaion

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mabrouk.core.utils.getCurrentDate
import com.mabrouk.sportingapp.NfcReaderManger
import com.mabrouk.sportingapp.persentaion.theme.SportingAppTheme
import com.mabrouk.sportingapp.persentaion.view.home
import com.mabrouk.sportingapp.persentaion.viewmodels.EventsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val nfcManager = NfcReaderManger(this) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    home()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcManager.enable()
    }

    override fun onPause() {
        super.onPause()
        nfcManager.disable()
    }
}