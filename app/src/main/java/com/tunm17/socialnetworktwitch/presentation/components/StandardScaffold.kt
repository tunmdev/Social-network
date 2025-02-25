package com.tunm17.socialnetworktwitch.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Doorbell
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tunm17.socialnetworktwitch.R
import com.tunm17.socialnetworktwitch.domain.models.BottomNavItem
import com.tunm17.socialnetworktwitch.ui.theme.HintGray
import com.tunm17.socialnetworktwitch.ui.theme.MediumGray
import com.tunm17.socialnetworktwitch.ui.theme.TextGray
import com.tunm17.socialnetworktwitch.ui.util.Screen

private val bottomNavItems = listOf<BottomNavItem>(
    BottomNavItem(
        route = Screen.MainFeedScreen.route,
        icon = Icons.Outlined.Home,
        contentDescription = "Home"
    ),
    BottomNavItem(
        route = Screen.ChatScreen.route,
        icon = Icons.Outlined.Message,
        contentDescription = "Message"
    ),
    BottomNavItem(
        route = Screen.ActivityScreen.route,
        icon = Icons.Outlined.Doorbell,
        contentDescription = "Activity"
    ),
    BottomNavItem(
        route = Screen.ProfileScreen.route,
        icon = Icons.Outlined.Person,
        contentDescription = "Profile"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    showToolbar: Boolean = true,
    toolbarTitle: String? = null,
    showBackArrow: Boolean = true,
    navActions: @Composable RowScope.() -> Unit = {},
    viewModel: StandardScaffoldViewModel = hiltViewModel(),
    bottomNavItems: List<BottomNavItem> = listOf<BottomNavItem>(
        BottomNavItem(
            route = Screen.MainFeedScreen.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home",
            alertCount = 4
        ),
        BottomNavItem(
            route = Screen.ChatScreen.route,
            icon = Icons.Outlined.Message,
            contentDescription = "Message"
        ),
        BottomNavItem(
            route = Screen.ActivityScreen.route,
            icon = Icons.Outlined.Notifications,
            contentDescription = "Activity"
        ),
        BottomNavItem(
            route = Screen.ProfileScreen.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile"
        ),
    ),
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
//        topBar = {
//            if (showToolbar) {
//                TopAppBar (
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.surface,
//                    ),
//                    title = {
//                        Text(
//                            text = toolbarTitle ?: "Twitch",
//                            fontWeight = FontWeight.Bold
//                        )
//                    },
//                    navigationIcon = {
//                        if (showBackArrow) {
//                            IconButton(onClick = {
//                                navController.popBackStack()
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.ArrowBack,
//                                    contentDescription = stringResource(id = R.string.back)
//                                )
//                            }
//                        }
//                    },
//                    actions = navActions
//                )
//            }
//        },
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.DarkGray
                    ) {
                        bottomNavItems.forEachIndexed { _, bottomNavItem ->
                            StandardBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = bottomNavItem.route == navController.currentDestination?.route,
                                alertCount = bottomNavItem.alertCount
                            ) {
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route)
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = modifier,
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = onFabClick,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.make_post)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        content()
    }
}