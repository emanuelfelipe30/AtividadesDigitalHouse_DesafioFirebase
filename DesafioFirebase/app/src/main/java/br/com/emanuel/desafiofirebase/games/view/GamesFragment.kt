package br.com.emanuel.desafiofirebase.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R

class GamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeSplashScreenOfBackStack()
        removeLoginScreenOfBackStack()
    }

    private fun removeSplashScreenOfBackStack() {
        Navigation.findNavController(requireView()).popBackStack(
            R.id.splashFragment, true)
    }

    private fun removeLoginScreenOfBackStack() {
        Navigation.findNavController(requireView()).popBackStack(
            R.id.loginFragment, true)
    }

}