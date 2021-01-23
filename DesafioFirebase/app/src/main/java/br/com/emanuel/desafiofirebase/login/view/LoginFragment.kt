package br.com.emanuel.desafiofirebase.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        removeSplashScreenOfBackStack()
        view.findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.registerFragment)
        }

    }

    private fun removeSplashScreenOfBackStack() {
        Navigation.findNavController(requireView()).popBackStack(
            R.id.splashFragment, true)
    }

}