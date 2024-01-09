package dev.mateuy.decomposenavigationsample.ui.authenticated.countdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ListView(component: ListComponent){
    val list by component.list.collectAsState()
    Column{
        LazyColumn{
            items(list) {
                Button(onClick = { component.onItemSelected(it) }){
                    Text("Count to $it")
                }
            }
        }
    }
}
