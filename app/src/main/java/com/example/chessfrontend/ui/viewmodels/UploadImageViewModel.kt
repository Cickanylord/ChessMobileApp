package com.example.chessfrontend.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chessfrontend.data.UserRepository
import com.example.chessfrontend.data.netwrok.ChessApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@HiltViewModel
class UploadImageViewModel @Inject constructor(
    private val chessApiService: ChessApiService,
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {
    var uiState by mutableStateOf(UploadImageUiState())
        private set

    fun handleAction(action: UploadImageAction) {
        when (action) {
            is UploadImageAction.SelectImage -> onSelectImage(action.uri)
            is UploadImageAction.UploadImage -> uploadImage(action.onResult)
        }
    }

    private fun onSelectImage(uri: Uri) {
        uiState = uiState.copy(
            selectedImageUri = uri
        )
    }

    private fun uploadImage(
        onResult: (Boolean) -> Unit
    ) {
        val compressedImage = compressImage(uiState.selectedImageUri!!)

        if (compressedImage != null) {
            uiState = uiState.copy(
                isUploading = true
            )

            val requestBody = MultipartBody.Part.createFormData(
                "multipartImage",
                "upload.jpg",
                compressedImage.toRequestBody("image/jpeg".toMediaTypeOrNull())
            )

            viewModelScope.launch {
                try {
                    val response = chessApiService.uploadImage(requestBody)
                    userRepository.getProfile()
                    withContext(Dispatchers.Main) {
                        onResult(true)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        onResult(false)
                    }
                } finally {
                    uiState = uiState.copy(
                        isUploading = false
                    )
                }
            }
        } else {
            onResult(false)
        }
    }


    private fun compressImage(uri: Uri): ByteArray? {
        val maxFileSize = 1_048_576
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        var compressedByteArray: ByteArray
        var quality = 100

        do {
            val outputStream = ByteArrayOutputStream()
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            compressedByteArray = outputStream.toByteArray()
            quality -= 10
        } while (compressedByteArray.size > maxFileSize && quality > 0)

        return if (compressedByteArray.size <= maxFileSize) compressedByteArray else null
    }
}



sealed interface UploadImageAction {
    data class SelectImage(val uri: Uri) : UploadImageAction
    data class UploadImage(val onResult: (Boolean) -> Unit) : UploadImageAction
}

data class UploadImageUiState(
    val selectedImageUri: Uri? = null,
    val isUploading: Boolean = false
)

