package br.com.emanuel.desafiofirebase.register.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _auth: FirebaseAuth

    private lateinit var edtNameRegister: TextInputEditText
    private lateinit var edtLayoutNameRegister: TextInputLayout

    private lateinit var edtEmailRegister: TextInputEditText
    private lateinit var edtLayoutEmailRegister: TextInputLayout

    private lateinit var edtPasswordRegister: TextInputEditText
    private lateinit var edtLayoutPasswordRegister: TextInputLayout

    private lateinit var edtRepeatPasswordRegister: TextInputEditText
    private lateinit var edtLayoutRepeatPasswordRegister: TextInputLayout

    private lateinit var btnCreateAccount: Button

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myView = view
        _auth = Firebase.auth

        initializeViews()
        btnCreateAccount.setOnClickListener {
            if (validateFields()) {
                createAccount(
                    edtNameRegister.text.toString(),
                    edtEmailRegister.text.toString(),
                    edtPasswordRegister.text.toString()
                )
            }
        }

    }

    private fun initializeViews() {

        edtNameRegister = _myView.findViewById(R.id.edtNameRegister)
        edtLayoutNameRegister = _myView.findViewById(R.id.edtLayoutNameRegister)

        edtEmailRegister = _myView.findViewById(R.id.edtEmailRegister)
        edtLayoutEmailRegister = _myView.findViewById(R.id.edtLayoutEmailRegister)

        edtPasswordRegister = _myView.findViewById(R.id.edtPasswordRegister)
        edtLayoutPasswordRegister = _myView.findViewById(R.id.edtLayoutPasswordRegister)

        edtRepeatPasswordRegister = _myView.findViewById(R.id.edtRepeatPasswordRegister)
        edtLayoutRepeatPasswordRegister = _myView.findViewById(R.id.edtLayoutRepeatPasswordRegister)

        btnCreateAccount = _myView.findViewById(R.id.btnCreateAccount)

    }

    private fun validateFields(): Boolean {
        var response = true

        if (edtNameRegister.text.isNullOrBlank()) {
            edtLayoutNameRegister.error = "Please type your Name"
            response = false
        } else edtLayoutNameRegister.error = null

        if (edtEmailRegister.text.isNullOrBlank()) {
            edtLayoutEmailRegister.error = "Please type your e-mail"
            response = false
        } else edtLayoutEmailRegister.error = null

        when {
            edtPasswordRegister.text.isNullOrBlank() -> {
                edtLayoutPasswordRegister.error = "Password Required"
                response = false
            }
            edtPasswordRegister.text!!.length < 6 -> {
                edtLayoutPasswordRegister.error = "Password must be at least 6 characters long"
                response = false
            }
            else -> edtLayoutPasswordRegister.error = null
        }

        if (edtRepeatPasswordRegister.text.isNullOrBlank()) {
            edtLayoutRepeatPasswordRegister.error = "Please repeat your password"
            response = false
        } else {
            edtLayoutRepeatPasswordRegister.error = null
            if (edtPasswordRegister.text.toString() != edtRepeatPasswordRegister.text.toString()){
                edtPasswordRegister.error = "Passwords do not match"
                response = false
            } else edtPasswordRegister.error = null
        }

        return response
    }

    private fun createAccount(name: String, email: String, password: String) {
        activity?.let {
            _auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(it) { task ->

                if (task.isSuccessful) {

                    val user = _auth.currentUser
                    val profileUpdates = userProfileChangeRequest { displayName = name }

                    user!!.updateProfile(profileUpdates).addOnCompleteListener {
                        Toast.makeText(
                            _myView.context, "User successfully created", Toast.LENGTH_SHORT
                        ).show()
                        Navigation.findNavController(_myView).navigate(R.id.loginFragment)
                    }

                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        _myView.context, "Authentication failed", Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

}