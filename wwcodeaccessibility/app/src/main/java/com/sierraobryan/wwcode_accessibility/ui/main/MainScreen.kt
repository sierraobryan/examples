package com.sierraobryan.wwcode_accessibility.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sierraobryan.wwcode_accessibility.data.models.Author
import com.sierraobryan.wwcode_accessibility.data.models.Commit
import com.sierraobryan.wwcode_accessibility.data.models.CommitDetails
import com.sierraobryan.wwcode_accessibility.ui.components.AppTextField
import com.sierraobryan.wwcode_accessibility.ui.components.CardComposable


@Composable
fun MainScreen() {
//    val name: String by viewModel.username.observeAsState("sierraobryan")
//    val repoName: String by viewModel.repoName.observeAsState("hackerNews")
//    val commits: List<Commit> by viewModel.commits.observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 12.dp, end = 12.dp)) {
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            value = "name",
            placeholder = { Text(text = "Username") },
            onValueChanged = {
                //viewModel.username.postValue(it)
            }
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.clickable {  }.size(48.dp).padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        AppTextField(
            value = "repoName",
            placeholder = { Text(text = "Repository") },
            onValueChanged = {
                // viewModel.repoName.postValue(it)
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Button(onClick = {  }, modifier = Modifier.fillMaxWidth(fraction = .5f)) {
                CardComposable(commit = Commit(
                    sha = "sha",
                    commit = CommitDetails(
                        message = "message",
                        author = Author(name = "Sierra", date = "today")
                    )
                ))
            }
            Button(onClick = {  }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "FETCH COMMITS")
            }
            IconButton(onClick = { /*TODO*/ }) {
                
            }
        }
//        LazyColumn {
//            items(commits) {
//                CardComposable(commit = it)
//            }
//        }
    }
}