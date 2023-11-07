package com.example.deportestr.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.deportestr.R
import com.example.deportestr.navigation.AppScreens
import com.example.deportestr.ui.models.Sport
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    goLogin: NavHostController,
    goProfile: NavHostController,
    goFormula: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            DrawerContent(goLogin = goLogin, goProfile = goProfile) {
                coroutineScope.launch { drawerState.close() }
            }
        }
    }, drawerState = drawerState) {
        Scaffold(topBar = {
            TopBarContent(onClickDrawer = { coroutineScope.launch { drawerState.open() } })
        }) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                HomeBody(goFormula)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBody(goFormula: NavHostController) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val sports = getSports()
    val pagerState = rememberPagerState {
        sports.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
        selectedTabIndex = pagerState.currentPage
    }

    Column {
        ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
            sports.forEachIndexed { index, sport ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = sport.name) }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            Box(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .clickable { }
                        .fillMaxWidth()
                        .background(Color(0xFF303030)),
                    shape = MaterialTheme.shapes.small
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = sports[index].photo),
                            contentDescription = null,
                            Modifier.fillMaxWidth(),
                        )
                        Row(modifier = Modifier.align(Alignment.Start)) {
                            Text(
                                text = sports[index].name,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(2.dp)
                            )
                            Icon(imageVector = Icons.Filled.NavigateNext, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemSport(sport: Sport, goFormula: NavHostController) {


}

fun getSports(): List<Sport> {
    return listOf(
        Sport(1, "Futbol base de datos", R.drawable.futbol),
        Sport(2, "Formula 1 base de datos", R.drawable.f_uno),
        Sport(3, "Tenis base de datos", R.drawable.tenis),
        Sport(4, "MotoGP base de datos", R.drawable.motogp),
        Sport(5, "Baloncesto base de datos", R.drawable.baloncesto),
        Sport(6, "WRC base de datos", R.drawable.wrc)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent(onClickDrawer: () -> Unit) {
    TopAppBar(

        title = {
            Image(
                painter = painterResource(id = R.drawable.logo_bueno),
                contentDescription = null,
                modifier = Modifier
                    .size(175.dp)
            )
        },
        actions = {
            IconButton(onClick = { onClickDrawer() }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.Red)
    )
}

@Composable
fun DrawerContent(
    goLogin: NavHostController,
    goProfile: NavHostController,
    onCloseDrawer: () -> Job
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .clickable { onCloseDrawer() }
                    .size(100.dp)
            )
            Column {
                Text(text = "Big Boss")
                Text(text = "@big_boss.oh")
            }
        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(), color = Color(0xFF757575)
        )
        Row(modifier = Modifier
            .clickable { goProfile.navigate(route = AppScreens.ProfileScreen.route) }
            .fillMaxWidth()
        ) {
            Text(text = "Ir a mi perfil", fontSize = 25.sp)
            Icon(
                imageVector = Icons.Filled.NavigateNext,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(35.dp)
            )
        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(), color = Color(0xFF757575)
        )
        Row(modifier = Modifier
            .clickable { goLogin.navigate(route = AppScreens.LoginScreen.route) }
            .fillMaxWidth()

        ) {
            Text(text = "Cerrar session", color = Color(0xFFC70606), fontSize = 25.sp)
            Icon(
                imageVector = Icons.Filled.ExitToApp,
                contentDescription = null,
                tint = Color(0xFFC70606),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(35.dp)
            )
        }
    }
}

data class TabItem(
    val title: String
)
