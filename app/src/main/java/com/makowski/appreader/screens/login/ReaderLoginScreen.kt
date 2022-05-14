package com.makowski.appreader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.makowski.appreader.R
import com.makowski.appreader.components.EmailInput
import com.makowski.appreader.components.PasswordInput
import com.makowski.appreader.components.ReaderLogo
import com.makowski.appreader.navigation.ReaderScreens

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel = viewModel()){
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            ReaderLogo()
            if (showLoginForm.value) UserForm(loading = false, isCreateAccount = false){email, password ->
                viewModel.signInWithEmailAndPassword(email, password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }
            else{
                UserForm(loading = false, isCreateAccount = true){email, password ->
                    viewModel.createUserWithEmailAndPassword(email, password){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                    
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if (showLoginForm.value) "Sign up" else "Login"
            Text(text = "New User?")
            Text(text,
                modifier = Modifier
                    .clickable { showLoginForm.value = !showLoginForm.value }
                    .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant)
        }
        
    }

}

@ExperimentalComposeUiApi
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, password ->}
){

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember (email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(
            rememberScrollState()
        )
    
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct), modifier = Modifier.padding(4.dp))
        else Text(text = "")
        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus()})
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelID = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim()) })
        SubmitButton(
            textID = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()}
    }

}

@Composable
fun SubmitButton(textID: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
            onClick = onClick,
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth(),
            enabled = !loading && validInputs,
            shape = CircleShape) {
                if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
                else Text(text = textID, modifier = Modifier.padding(5.dp))
        
    }

}



