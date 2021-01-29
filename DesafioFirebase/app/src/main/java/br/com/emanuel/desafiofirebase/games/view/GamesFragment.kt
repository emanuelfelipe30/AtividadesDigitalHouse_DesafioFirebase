package br.com.emanuel.desafiofirebase.games.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeLoginScreenOfBackStack()
        view.findViewById<FloatingActionButton>(R.id.fabNewGame).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.gameEditFragment)
        }
    }

    private fun removeLoginScreenOfBackStack() {
        Navigation.findNavController(requireView()).popBackStack(
            R.id.loginFragment, true)
    }

}