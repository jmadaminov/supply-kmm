package uz.uzkass.smartpos.supply.android.ui.main.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer76dp
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField3
import uz.uzkass.smartpos.supply.android.ui.destinations.SubCategoriesScreenDestination
import uz.uzkass.smartpos.supply.android.ui.main.navigation.MainNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.CategoriesScreenState
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.CategoriesViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model.CategoryModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models.ProductItemModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkassa.smartpos.supply.library.MR

@MainNavGraph
@Composable
@Destination
fun CategoriesScreen(
    navigator: DestinationsNavigator,
    viewModel: CategoriesViewModel = koinViewModel()
) {
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onRefresh()
    })

    CategoriesScreenView(
        screenState = screenState.value,
        onCLickItem = {
            navigator.navigate(
                SubCategoriesScreenDestination(
                    branchId = screenState.value.currentBranch?.id?.toLongOrNull()?:0,
                    parentId = it.id!!
                )
            )
        },
        onSelectBranch = {
            viewModel.getCategoryList(
                branchId = it?.id?.toLongOrNull()
            )
            viewModel.selectBranch(it)
        },
        onRefresh = {
            viewModel.onRefresh()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CategoriesScreenView(
    screenState: CategoriesScreenState,
    onCLickItem: (CategoryModel) -> Unit,
    onSelectBranch: (DropdownModel?) -> Unit,
    onRefresh: () -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = screenState.loading,
        onRefresh = onRefresh
    )

    Scaffold(modifier = Modifier

        .fillMaxSize(),
        topBar = {

        }) {

        Column(
            modifier = Modifier
                .systemBarsPadding()
                .padding(16.dp)
                .pullRefresh(pullRefreshState)
                .background(color = Color.White),

            ) {

            if (!screenState.branchList.isNullOrEmpty()) {
                ExposedDropdownField3(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = MR.strings.branch.resourceId),
                    items = screenState.branchList,
                    currentItem = screenState.currentBranch,
                    autoSelectFirst = true,
                    onItemSelected = onSelectBranch
                )
                Spacer16dp()
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(screenState.categoryList) { item ->
                    val text = if ((item.name?.length ?: 0) > 25) {
                        item.name!!.substring(0, 24)
                    } else {
                        item.name ?: ""
                    }
                    CategoryItem(
                        name = text,
                        count = item.productCount ?: 0
                    ) {
                        onCLickItem(item)
                    }
                }

                item {
                    Spacer76dp()
                }
            }
        }
    }

}

@Composable
fun CategoryItem(
    name: String,
    count: Long,
    onCLickItem: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF4F3FF),
                shape = LocalShapes.current.small8Dp
            )
            .clickable(onClick = onCLickItem)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1
        )

        FillAvailableSpace()
        Text(
            text = "$count",
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null
        )

    }
}

