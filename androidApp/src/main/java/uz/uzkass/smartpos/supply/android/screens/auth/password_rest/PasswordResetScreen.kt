package uz.uzkass.smartpos.supply.android.screens.auth.password_rest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.core.PhoneWithPrefixTransformation
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.LabelTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR

@Composable
fun PasswordResetScreen() {

//    val viewModel: PasswordResetViewModel = hiltViewModel()

    PasswordResetScreenView(
        onClickReset = { }
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PasswordResetScreenView(onClickReset: (String) -> Unit) {

    val keyboard = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(id = MR.strings.reset_password.resourceId),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = SupplyTheme.colors.largeTitle
            )
            Text(
                text = stringResource(id = MR.strings.reset_password_info.resourceId),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.mediumTitle
            )
            Spacer24dp()
            val valuePhone = remember {
                mutableStateOf(TextFieldValue())
            }

            LabelTextField(
                label = stringResource(id = MR.strings.phone_number.resourceId),
                valueState = valuePhone,
                visualTransformation = PhoneWithPrefixTransformation("+998"),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onClickReset(valuePhone.value.text)
                    })
            )

            FillAvailableSpace()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    onClickReset(valuePhone.value.text)
                }) {
                Text(
                    text = stringResource(id = MR.strings.confirm.resourceId),
                    style = SupplyTheme.typography.button
                )
            }

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PasswordResetScreenPreview() {
    SupplyTheme() {
        PasswordResetScreenView() {

        }
    }

}