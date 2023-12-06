package com.example.contacts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
                    MainContent(onAddClick = {
                        val intent = Intent(this@MainActivity, AddContactActivity::class.java)
                        startActivity(intent)
                    })
                }
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
        floatingActionButtonPosition = FabPosition.End
    )
    {
        var contactsItems by remember {
            mutableStateOf(listOf<Contacts>())
        }
        //fetch initial data from room database
        contactsItems =
            ContactsDatabase.getInstance(LocalContext.current).getContactsDao().getAllContacts()

        LazyColumn(
            Modifier.padding(it.calculateTopPadding())
        ) {
            items(contactsItems.size) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp, horizontal = 16.dp
                        ), shape = CardDefaults.outlinedShape
                ) {
                    val items = contactsItems[it]
                    Text(
                        text = items.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = items.number,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )

                }
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactsTheme {
        MainContent {}
    }
}