package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.DividerMin
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.SupplyTextButton
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField2
import uz.uzkass.smartpos.supply.android.coreui.radiobutton.LabeledRadioButton
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderState
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderViewModel
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileScreenState
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun ConfirmOrderScreen(
    navigator: DestinationsNavigator,

    viewModel: ConfirmOrderViewModel = koinViewModel()
) {

    val screenState = viewModel.screenState.collectAsState()


    ConfirmOrderScreenView(
        screenState = screenState.value,
        onConfirm = {
            viewModel.confirmOrder()
        },
        onSaveTo = {

        },
        onClickBack = navigator::popBackStack
    )


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmOrderScreenView(
    screenState: ConfirmOrderState,
    onConfirm: () -> Unit,
    onSaveTo: () -> Unit,
    onClickBack: () -> Unit
) {
    Scaffold(topBar = {
        ConfirmOrderAppBar(onClickBack)
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TwoSiteText(
                modifier = Modifier.fillMaxWidth(),
                label = "Кол-во товаров:",
                value = screenState.productCount
            )

            Spacer8dp()
            DividerMin()
            Spacer8dp()

            TwoSiteText(
                modifier = Modifier.fillMaxWidth(),
                label = "Общая сумма:",
                value =screenState.summa
            )

            Spacer3dp()

            TwoSiteText(
                modifier = Modifier.fillMaxWidth(),
                label = "НДС 10%",
                value =screenState.vatAmount
            )

            Spacer16dp()

            ExposedDropdownField2(
                items = screenState.paymentType,
                label = "Payment type",
                onItemSelected = {  },
            )

            Spacer16dp()

            ExposedDropdownField2(
                items = screenState.saleType,
                label = "Sale type",
                onItemSelected = {  },
            )

            Spacer16dp()

            FillAvailableSpace()

            SupplyFilledTextButton(
                text = "Оформить заказ",
                onClick = onConfirm
            )

            SupplyTextButton(
                text = "Сохранить черновик",
                onClick = onSaveTo
            )

        }
    }

}

const val firstRadioId = "firstRadioId"
const val secondRadioId = "secondRadioId"
const val threeRadioId = "threeRadioId"

@Composable
fun ChoosePaymentTypeRadioButton(onItemSelect: (String) -> Unit) {
    var currentRadio by remember {
        mutableStateOf(firstRadioId)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Тип оплаты",
            fontSize = 16.sp,
            color = SupplyTheme.colors.textButtonText,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabeledRadioButton(
                modifier = Modifier,
                selected = currentRadio == firstRadioId,
                label = "Консигнация",
                onClick = {
                    onItemSelect(firstRadioId)
                    currentRadio = firstRadioId
                }
            )

            FillAvailableSpace()
            LabeledRadioButton(
                modifier = Modifier,
                selected = currentRadio == secondRadioId,
                label = "Другое",
                onClick = {
                    onItemSelect(secondRadioId)
                    currentRadio = secondRadioId
                }
            )
            FillAvailableSpace()
        }
        LabeledRadioButton(
            modifier = Modifier,
            selected = currentRadio == threeRadioId,
            label = "Предоплата",
            onClick = {
                onItemSelect(threeRadioId)
                currentRadio = threeRadioId
            }
        )
    }
}

@Composable
private fun TwoSiteText(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    color: Color = SupplyTheme.colors.h2
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = color)
        FillAvailableSpace()
        Text(text = value, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = color)
    }
}

@Composable
private fun ConfirmOrderAppBar(onClickBack: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onClickBack
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Confirm Order"
        )

    }
}


@Composable
fun DisableTextWithLabel(
    modifier: Modifier = Modifier,
    label: String, text: String
) {

    Column(modifier = modifier) {
        LabelText(label = label)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF2F4F7),
                    shape = LocalShapes.current.small8Dp
                ),
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
    }

}