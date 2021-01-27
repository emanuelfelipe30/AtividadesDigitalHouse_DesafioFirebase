package br.com.emanuel.desafiofirebase.login.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.navigation.Navigation
import br.com.emanuel.desafiofirebase.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _auth: FirebaseAuth

    private lateinit var edtEmailLogin: TextInputEditText
    private lateinit var edtLayoutEmailLogin: TextInputLayout

    private lateinit var edtPasswordLogin: TextInputEditText
    private lateinit var edtLayoutPasswordLogin: TextInputLayout

    private lateinit var checkRememberLogin: CheckBox
    private lateinit var btnLogin: Button
    private lateinit var btnNavigateToCreateAccount: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myView = view
        _auth = FirebaseAuth.getInstance()

        removeSplashScreenOfBackStack()
        initializeViews()

        btnLogin.setOnClickListener {
            if(validateFields()) {
                doLogin(
                    edtEmailLogin.text.toString(),
                    edtPasswordLogin.text.toString()
                )
            }
        }

        btnNavigateToCreateAccount.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.registerFragment)
        }

    }

    private fun removeSplashScreenOfBackStack() {
        Navigation.findNavController(requireView()).popBackStack(
            R.id.splashFragment, true)
    }

    private fun initializeViews() {

        edtEmailLogin = _myView.findViewById(R.id.edtEmailLogin)
        edtLayoutEmailLogin = _myView.findViewById(R.id.edtLayoutEmailLogin)

        edtPasswordLogin = _myView.findViewById(R.id.edtPasswordLogin)
        edtLayoutPasswordLogin = _myView.findViewById(R.id.edtLayoutPasswordLogin)

        checkRememberLogin = _myView.findViewById(R.id.checkRememberLogin)
        btnLogin = _myView.findViewById(R.id.btnLogin)
        btnNavigateToCreateAccount = _myView.findViewById(R.id.btnNavigateToCreateAccount)

    }

    private fun validateFields(): Boolean {
        var response = true

        if (edtEmailLogin.text.isNullOrBlank()) {
            edtLayoutEmailLogin.error = "Please type your e-mail"
            response = false
        } else edtLayoutEmailLogin.error = null

        when {
            edtPasswordLogin.text.isNullOrBlank() -> {
                edtLayoutPasswordLogin.error = "Password Required"
                response = false
            }
            edtPasswordLogin.text!!.length < 6 -> {
                edtLayoutPasswordLogin.error = "Password must be at least 6 characters long"
                response = false
            }
            else -> edtLayoutPasswordLogin.error = null
        }

        return response
    }

    private fun doLogin(email: String, password: String) {

        activity?.let {
            _auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(it) { task ->
                if (task.isSuccessful) {
                    Navigation.findNavController(_myView).navigate(R.id.gamesFragment)
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        _myView.context, "Email or password is wrong", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

}