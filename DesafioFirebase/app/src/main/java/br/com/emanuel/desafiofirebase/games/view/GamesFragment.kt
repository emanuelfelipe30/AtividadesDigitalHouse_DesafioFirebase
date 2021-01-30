package br.com.emanuel.desafiofirebase.games.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emanuel.desafiofirebase.R
import br.com.emanuel.desafiofirebase.game.model.GameModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class GamesFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _user: FirebaseUser
    private lateinit var _databaseRef: DatabaseReference
    private lateinit var _gamesAdapter: GamesAdapter

    private var _games = mutableListOf<GameModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //removeLoginScreenOfBackStack()

        _myView = view
        _auth = FirebaseAuth.getInstance()
        _user = _auth.currentUser!!

        _databaseRef = FirebaseDatabase.getInstance().getReference("users").child(_user.uid)
        createList(_games)

        _databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach() {
                    val data = it.getValue(GameModel::class.java)!!
                    _games.add(
                        GameModel(
                            data.name,
                            data.createdAt,
                            data.description,
                            data.image
                        )
                    )
                }
                _gamesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "loadPost:onCancelled", error.toException())
            }
        })

        view.findViewById<FloatingActionButton>(R.id.fabNewGame).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.gameEditFragment)
        }
    }

    /*private fun removeLoginScreenOfBackStack() {
        Navigation.findNavController(requireView()).popBackStack(
            R.id.loginFragment, true)
    }*/

    private fun createList(games: List<GameModel>) {
        _games.clear()
        val recyclerView = _myView.findViewById<RecyclerView>(R.id.recyclerLevels)
        val manager = GridLayoutManager(_myView.context, 2)

        _gamesAdapter = GamesAdapter(games) {

        }

        recyclerView.apply {
            setHasFixedSize(true)

            layoutManager = manager
            adapter = _gamesAdapter
        }
    }

}