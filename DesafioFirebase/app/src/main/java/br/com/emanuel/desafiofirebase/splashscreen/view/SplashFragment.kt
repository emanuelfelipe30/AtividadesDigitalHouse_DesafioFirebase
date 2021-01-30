package br.com.emanuel.desafiofirebase.splashscreen.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R
import com.google.firebase.auth.FirebaseAuth

private const val SPLASH_DURATION = 4000L

class SplashFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myView = view
        _auth = FirebaseAuth.getInstance()
        _navController = Navigation.findNavController(view)
        closeSplashScreen()

    }

    private fun closeSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            if(_auth.currentUser == null){
                _navController.navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
            _navController.navigate(SplashFragmentDirections.actionSplashFragmentToGamesFragment())
        }, SPLASH_DURATION)
    }

}