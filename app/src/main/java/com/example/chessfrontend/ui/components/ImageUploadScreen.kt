package com.example.chessfrontend.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.chessfrontend.ui.viewmodels.UploadImageAction
import com.example.chessfrontend.ui.viewmodels.UploadImageUiState
import com.example.chessfrontend.ui.viewmodels.UploadImageViewModel


@Composable
fun UploadPictureRoot(
    uploadImageViewModel: UploadImageViewModel,
) {
    UploadPictureScreen(
        state = uploadImageViewModel.uiState,
        onAction = uploadImageViewModel::handleAction,
        onUploadSuccess = {}
    )
}


@Composable
fun UploadPictureScreen(
    state: UploadImageUiState,
    onAction: (UploadImageAction) -> Unit,
    onUploadSuccess: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            onAction(UploadImageAction.SelectImage(uri))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the selected image
        if (state.selectedImageUri != null) {
            MyCircularProfilePicture(
                modifier = Modifier.size(150.dp),
                model = state.selectedImageUri
            )
        } else {
            Text("No image selected", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Select Image Button
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Upload Image Button
        Button(
            onClick = {
                onAction(UploadImageAction.UploadImage(
                    onResult = { success ->
                        if (success) {
                            onUploadSuccess()
                        } else {
                            Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                ))
            },
            enabled = state.selectedImageUri != null && !state.isUploading
        ) {
            if (state.isUploading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Upload Image")
            }
        }
    }
}






