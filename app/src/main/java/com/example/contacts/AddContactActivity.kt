package com.example.contacts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contacts.database.Contacts
import com.example.contacts.database.ContactsDatabase
import com.example.contacts.ui.theme.ContactsTheme

class AddContactActivity : ComponentActivity() {

    private lateinit var coursesItems: List<Contacts>

    private val addContactLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK)
            coursesItems = ContactsDatabase.getInstance(this).getContactsDao().getAllContacts()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddContactContent(
                        onNavigationClick = { finish() },
                        onSaveButtonClick = {
                            addContactLauncher.launch(
                                Intent(
                                    this, MainActivity::class.java
                                )
                            )

                            finish()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddContactContent(
    onNavigationClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ContactsTopAppBar(
                navigationIconOnCLickListener = { onNavigationClick() },
                title = "Add New Contacts",
                navigationIcon = R.drawable.ic_arrow_back
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it.calculateTopPadding())
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val name = remember {
                mutableStateOf("")
            }
            val number = remember {
                mutableStateOf("")
            }
            ContactTextField(
                label = "Contacts Name",
                mutableState = name,
                keyboardType = KeyboardType.Text,
                maxChar = 30
            )
            Spacer(modifier = Modifier.height(8.dp))
            ContactTextField(
                label = "Contacts Number",
                mutableState = number,
                keyboardType = KeyboardType.Number,
                maxChar = 11
            )
            Button(
                onClick = {
                    //call room database to save contacts
                    ContactsDatabase.getInstance(context).getContactsDao()
                        .insertContact(
                            Contacts(
                                name = name.value,
                                number = number.value
                            )
                        )
                    onSaveButtonClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorOrange)),
                enabled = name.value.isNotEmpty() && number.value.length > 10
            )
            {
                Text(text = "Save Contacts")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactTextField(
    label: String,
    mutableState: MutableState<String>,
    keyboardType: KeyboardType,
    maxChar: Int
) {
    OutlinedTextField(
        value = mutableState.value,
        onValueChange = {
            if (it.length <= maxChar)
                mutableState.value = it
        },
        label = { Text(text = label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        supportingText = {
            Text(
                text = "${mutableState.value.length} / $maxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }, shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.colorOrange),
            focusedSupportingTextColor = Color.Red,
            unfocusedLabelColor = Color.LightGray,
            focusedLabelColor = Color.Red,
        )

    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ContactsTheme {
        AddContactContent({}, {})
    }
}