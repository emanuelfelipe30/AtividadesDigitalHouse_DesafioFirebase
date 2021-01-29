package br.com.emanuel.desafiofirebase.gameedit.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R
import br.com.emanuel.desafiofirebase.game.model.GameModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView

private const val REQUEST_CODE = 200

class GameEditFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _user: FirebaseUser
    private lateinit var _dataBaseReference: DatabaseReference
    private lateinit var _storageReference: StorageReference

    private lateinit var edtNameEditGame: TextInputEditText
    private lateinit var edtLayoutNameEditGame: TextInputLayout

    private lateinit var edtCreatedAtEditGame: TextInputEditText
    private lateinit var edtLayoutCreatedAtEditGame: TextInputLayout

    private lateinit var edtDescriptionAtEditGame: TextInputEditText
    private lateinit var edtLayoutDescriptionEditGame: TextInputLayout

    private lateinit var btnAddGameImage: CircleImageView
    private lateinit var btnSaveGame: Button

    private var gameImage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myView = view
        _auth = FirebaseAuth.getInstance()
        _user = _auth.currentUser!!
        _dataBaseReference = FirebaseDatabase.getInstance().getReference("users")
        _storageReference = FirebaseStorage.getInstance().getReference("uploads")

        initializeViews()

        btnAddGameImage.setOnClickListener {
            chooseImageGallery()
        }

        btnSaveGame.setOnClickListener {
            if(validateFields()) {
                saveGame()
            }
        }

    }

    private fun initializeViews() {

        edtNameEditGame = _myView.findViewById(R.id.edtNameEditGame)
        edtLayoutNameEditGame = _myView.findViewById(R.id.edtLayoutNameEditGame)

        edtCreatedAtEditGame = _myView.findViewById(R.id.edtCreatedAtEditGame)
        edtLayoutCreatedAtEditGame = _myView.findViewById(R.id.edtLayoutCreatedAtEditGame)

        edtDescriptionAtEditGame = _myView.findViewById(R.id.edtDescriptionAtEditGame)
        edtLayoutDescriptionEditGame = _myView.findViewById(R.id.edtLayoutDescriptionEditGame)

        btnAddGameImage = _myView.findViewById(R.id.btnAddGameImage)
        btnSaveGame = _myView.findViewById(R.id.btnSaveGame)

    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun validateFields(): Boolean {
        var success = true

        if (edtNameEditGame.text.isNullOrBlank()) {
            edtLayoutNameEditGame.error = "Please type the game name"
            success = false
        } else edtLayoutNameEditGame.error = null

        if (edtCreatedAtEditGame.text.isNullOrBlank()) {
            edtLayoutCreatedAtEditGame.error = "Please type the game creation year"
            success = false
        } else edtLayoutNameEditGame.error = null

        if (edtDescriptionAtEditGame.text.isNullOrBlank()) {
            edtLayoutDescriptionEditGame.error = "Please type the game description"
            success = false
        } else edtLayoutDescriptionEditGame.error = null

        if (gameImage == null) {
            Toast.makeText(
                _myView.context,
                "Please select one image for the game",
                Toast.LENGTH_SHORT
            ).show()
            success = false
        }

        return success
    }

    private fun saveGame() {

        btnSaveGame.isEnabled = false
        gameImage?.run {

            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(
                activity?.contentResolver?.getType(this)
            )

            val fileReference = _storageReference.child(_user.uid).child(
                "${System.currentTimeMillis()}.${extension}"
            )

            fileReference.putFile(this).addOnSuccessListener {
                fileReference.downloadUrl.addOnSuccessListener {
                    val game = GameModel(
                        edtNameEditGame.text.toString(),
                        edtCreatedAtEditGame.text.toString().toInt(),
                        edtDescriptionAtEditGame.text.toString(),
                        it.toString()
                    )
                    _dataBaseReference.child(_user.uid).child(game.name).setValue(game)
                    Toast.makeText(
                        _myView.context,
                        "Game saved with success",
                        Toast.LENGTH_SHORT
                    ).show()
                    Navigation.findNavController(requireView()).popBackStack(
                        R.id.gameEditFragment, true
                    )
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    _myView.context,
                    "Error saving image",
                    Toast.LENGTH_SHORT
                ).show()
                btnSaveGame.isEnabled = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            gameImage = data?.data
            btnAddGameImage.setImageURI(gameImage)
        }
    }

}