package com.example.android.dagger.registration.enterdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.android.dagger.MyApplication
import com.example.android.dagger.R
import com.example.android.dagger.registration.RegistrationActivity
import com.example.android.dagger.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EnterDetailsFragment : Fragment() {

/*    @Inject
    lateinit var registrationViewModel: RegistrationViewModel*/

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

/*    @Inject
    lateinit var enterDetailsViewModel: EnterDetailsViewModel*/

    private val enterDetailsViewModel: EnterDetailsViewModel by activityViewModels()

    private lateinit var errorTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        //LE DECIMOS A DAGGER QUE ESTE FRAGMENT REQUIERE DE INJECTION
        //(requireActivity().application as MyApplication).appComponent.inject(this)

        (requireActivity() as RegistrationActivity).registrationComponent.inject(this)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_enter_details, container, false
        )

        /*registrationViewModel = (
                activity as RegistrationActivity
        ).registrationViewModel*/
        //enterDetailsViewModel = EnterDetailsViewModel()


        enterDetailsViewModel.enterDetailsState.observe(
            viewLifecycleOwner
        ) {
            when (it) {

                is EnterDetailsSuccess -> {

                    val username = usernameEditText.text.toString()
                    val password = passwordEditText.text.toString()

                    registrationViewModel.updateUserData(
                        username, password
                    )

                    (activity as RegistrationActivity).onDetailsEntered()


                }

                is EnterDetailsError -> {
                    errorTextView.text = it.error
                    errorTextView.visibility = View.VISIBLE
                }

            }
        }

        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {

        errorTextView = view.findViewById(R.id.error)
        usernameEditText = view.findViewById(R.id.username)

        usernameEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }

        passwordEditText = view.findViewById(R.id.password)
        passwordEditText.doOnTextChanged { _, _, _, _ -> errorTextView.visibility = View.INVISIBLE }

        view.findViewById<Button>(R.id.next).setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            enterDetailsViewModel.validateInput(username, password)
        }
    }
}

sealed class EnterDetailsViewState
object EnterDetailsSuccess : EnterDetailsViewState()
data class EnterDetailsError(val error: String) : EnterDetailsViewState()
