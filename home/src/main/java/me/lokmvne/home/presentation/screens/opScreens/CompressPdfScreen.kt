package me.lokmvne.home.presentation.screens.opScreens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.lokmvne.common.utils.RealPathUtil.getRealPath
import me.lokmvne.home.R
import me.lokmvne.home.data.CompressService
import me.lokmvne.home.presentation.viewmodels.CompressPdfViewModel

@Composable
fun CompressPdfScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val compressPdfViewModel = hiltViewModel<CompressPdfViewModel>()
    val filePath = compressPdfViewModel.filePath
    val rendererPages by compressPdfViewModel.rendererPages.collectAsState()

    val pickFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            compressPdfViewModel.pdfToBitmap(uri)
            filePath.value = getRealPath(context, uri)
        }
    }

    if (rendererPages.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.selectpdf),
                contentDescription = "Compress PDF",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        pickFile.launch("application/pdf")
                    },
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            DisplayPDF(
                rendererPages,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        if (filePath.value != null || filePath.value != "") {
                            Intent(context, CompressService::class.java).also {
                                it.action =
                                    CompressService.PdfOperationsActions.START.toString()
                                it.putExtra("filePath", filePath.value)
                                context.startService(it)
                            }
                            filePath.value = null

                            compressPdfViewModel.closeRenderer()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("Compress File")
                }

                Button(
                    onClick = {
                        pickFile.launch("application/pdf")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text("Open Another File")
                }
            }
        }
    }
}


@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(modifier = modifier,
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Permission Required") },
        text = {
            Text(
                text = "Storage permission is required to Compress the PDF file",
            )
        },
        dismissButton = {
            Text(
                text = "Dismiss",
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(horizontal = 10.dp)
            )
        },
        confirmButton = {
            Text(
                text = "Grant",
                modifier = Modifier
                    .clickable { onGoToAppSettingsClick() }
                    .padding(horizontal = 10.dp)
            )
        }
    )
}
