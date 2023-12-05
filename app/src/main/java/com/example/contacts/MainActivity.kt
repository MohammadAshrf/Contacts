package com.example.contacts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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