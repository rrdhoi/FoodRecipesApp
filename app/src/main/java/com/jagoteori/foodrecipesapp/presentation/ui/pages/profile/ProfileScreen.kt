package com.jagoteori.foodrecipesapp.presentation.ui.pages.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jagoteori.foodrecipesapp.R
import com.jagoteori.foodrecipesapp.presentation.profile.ProfileViewModel
import com.jagoteori.foodrecipesapp.presentation.ui.components.ImagePickerDialog
import com.jagoteori.foodrecipesapp.presentation.ui.extention.noRippleClickable
import com.jagoteori.foodrecipesapp.presentation.ui.theme.BlackColor500
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor100
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor300
import com.jagoteori.foodrecipesapp.presentation.ui.theme.GreyColor500

@Composable
fun ProfileScreen(
    modifier: Modifier,
    profileViewModel: ProfileViewModel,
    onClickReports: () -> Unit,
    onSignOut: () -> Unit,
) {
    val context = LocalContext.current
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }

    val user = profileViewModel.myUser.data

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            profileViewModel.imagePickerDialogState = false
            profileViewModel.updateUser(user!!.copy(profilePicture = imageUri.toString()))
        }
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            profileViewModel.imagePickerDialogState = false
            profileViewModel.updateUser(user!!.copy(profilePicture = uri.toString()))
        }
    )


    Box(modifier = modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Profil Kamu",
                modifier = modifier.padding(top = 24.dp),
                style = TextStyle(
                    color = BlackColor500,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                )
            )

            if (user?.profilePicture.isNullOrEmpty())
                Image(
                    modifier = modifier
                        .height(125.dp)
                        .width(100.dp)
                        .padding(top = 24.dp)
                        .border(width = 1.dp, color = GreyColor300, shape = CircleShape)
                        .clip(CircleShape)
                        .noRippleClickable { profileViewModel.imagePickerDialogState = true },
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "No Image Profile",
                )
            else AsyncImage(
                model = user?.profilePicture,
                contentDescription = "Image Step Cook",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(125.dp)
                    .width(100.dp)
                    .padding(top = 24.dp)
                    .border(width = 1.dp, color = GreyColor300, shape = CircleShape)
                    .clip(CircleShape)
                    .noRippleClickable { profileViewModel.imagePickerDialogState = true },
            )

            Text(
                text = user?.name ?: "",
                modifier = modifier.padding(top = 12.dp),
                style = TextStyle(
                    color = BlackColor500,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600
                )
            )

            Text(
                text = user?.email ?: "",
                modifier = modifier.padding(top = 6.dp, bottom = 18.dp),
                style = TextStyle(
                    color = GreyColor500,
                    fontSize = 16.sp,
                )
            )

            ItemProfileMenu(
                modifier = modifier.noRippleClickable {
                    onClickReports()
                },
                title = "Daftar Resepku",
                icon = painterResource(id = R.drawable.ic_list),
            )

            ItemProfileMenu(
                modifier = modifier,
                title = "Bantuan",
                icon = painterResource(id = R.drawable.ic_help),
            )
            ItemProfileMenu(
                modifier = modifier,
                title = "Pengaturan",
                icon = painterResource(id = R.drawable.ic_baseline_settings_24),
            )
            ItemProfileMenu(
                modifier = modifier.noRippleClickable { onSignOut() },
                title = "Keluar",
                icon = painterResource(id = R.drawable.ic_baseline_exit_to_app_24),
                isLast = true
            )
        }

        ImagePickerDialog(
            modifier = modifier,
            dialogState = profileViewModel.imagePickerDialogState,
            context = context,
            cameraLauncher = cameraLauncher,
            imagePicker = imagePicker,
            onGetImage = { uri ->
                imageUri = uri
                hasImage = false
            },
            onDismissRequest = {
                profileViewModel.imagePickerDialogState = false
            })
    }
}

@Composable
fun ItemProfileMenu(modifier: Modifier, title: String, icon: Painter, isLast: Boolean = false) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier
                    .size(36.dp)
                    .padding(end = 10.dp),
                painter = icon,
                contentDescription = "Icon List"
            )

            Text(
                text = title,
                style = TextStyle(
                    color = BlackColor500,
                    fontSize = 16.sp,
                )
            )
        }
        if (!isLast) Divider(color = GreyColor100, thickness = 1.dp)
    }

}
