package br.com.emanuel.desafiofirebase.games.view

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.com.emanuel.desafiofirebase.R
import br.com.emanuel.desafiofirebase.game.model.GameModel

class GamesAdapter(
    private val dataSet: List<GameModel>,
    private val listener: (GameModel) -> Unit
): RecyclerView.Adapter<GamesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GamesViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

}