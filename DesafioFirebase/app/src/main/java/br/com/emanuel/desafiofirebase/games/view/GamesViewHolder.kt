package br.com.emanuel.desafiofirebase.games.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.emanuel.desafiofirebase.R
import br.com.emanuel.desafiofirebase.game.model.GameModel
import com.bumptech.glide.Glide

class GamesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val imgGame = view.findViewById<ImageView>(R.id.imgGame)
    private val txtTitleGame = view.findViewById<TextView>(R.id.txtTitleGame)
    private val txtSubtitleGame = view.findViewById<TextView>(R.id.txtSubtitleGame)

    fun bind(game: GameModel) {
        Glide.with(itemView).load(game.image).into(imgGame)
        txtTitleGame.text = game.name
        txtSubtitleGame.text = game.createdAt.toString()
    }

}