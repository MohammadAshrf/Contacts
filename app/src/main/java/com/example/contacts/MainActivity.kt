package com.example.contacts

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contacts.database.Contacts
import com.example.contacts.database.ContactsDatabase
import com.example.contacts.ui.theme.ContactsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ContactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(
                        onAddClick = {
                            val intent = Intent(this@MainActivity, AddContactActivity::class.java)
                            startActivity(intent)
                        },
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainContent(onAddClick: () -> Unit) {
        Scaffold(
            topBar = {
                ContactsTopAppBar(
                    navigationIcon = null,
                    navigationIconOnCLickListener = {},
                    title = "Your Contacts"
                )
            },
            floatingActionButton =
            {
                FloatingActionButton(
                    modifier = Modifier.padding(end = 8.dp, bottom = 16.dp),
                    onClick = { onAddClick() },
                    containerColor = colorResource(id = R.color.colorOrange),
                    contentColor = colorResource(id = R.color.white)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add Icon"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,

            )
        {
            val context = LocalContext.current


            var contactsItems by remember {
                mutableStateOf(listOf<Contacts>())
            }
            //fetch initial data from room database
            contactsItems =
                ContactsDatabase.getInstance(LocalContext.current).getContactsDao().getAllContacts()


            LazyColumn(
                Modifier.padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp)
            ) {
                items(contactsItems.size) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 8.dp
                            ), shape = RoundedCornerShape(16.dp)
                    ) {
                        val items = contactsItems[it]
                        Text(
                            text = "Name: ${items.name}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Number: ${items.number}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            textAlign = TextAlign.Center
                        )

                        IconButton(
                            onClick = {
                                val number = Uri.parse("tel:${items.number}")

                                // Create the intent and set the data for the
                                // intent as the phone number.
                                val intent = Intent(Intent.ACTION_DIAL, number)
                                try {

                                    // Launch the Phone app's dialer with a phone
                                    // number to dial a call.
                                    context.startActivity(intent)
                                } catch (s: SecurityException) {

                                    // show() method display the toast with
                                    // exception message.
                                    Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                                        .show()
                                }

                            },
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_call),
                                contentDescription = "call icon",
                                tint = colorResource(id = R.color.colorGreen),
                                modifier = Modifier.size(32.dp)
                            )
                        }


                    }
                }
            }

        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ContactsTheme {
            MainContent {}
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsTopAppBar(
    navigationIcon: Int? = null,
    navigationIconOnCLickListener: () -> Unit,
    title: String
) {
    TopAppBar(
        title = { Text(text = title) },
        colors =
        TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorResource(id = R.color.colorOrange),
            titleContentColor = colorResource(id = R.color.white),
            navigationIconContentColor = colorResource(id = R.color.white),
        ),
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = { navigationIconOnCLickListener() })
                {
                    Icon(
                        painter = painterResource(id = navigationIcon),
                        contentDescription = "Back Arrow"
                    )
                }
            }
        }

    )
}