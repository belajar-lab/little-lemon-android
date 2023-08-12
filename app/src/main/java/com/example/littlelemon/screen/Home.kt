package com.example.littlelemon.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.DishDetails
import com.example.littlelemon.MenuItemRoom
import com.example.littlelemon.Profile
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, dishes: List<MenuItemRoom>) {
    var searchPhrase by rememberSaveable { mutableStateOf("") }
    val selectedCategory = remember { mutableStateListOf<String>() }
    val categoryList =
        dishes.map { it.category }.distinct() //listOf("Starters", "Mains", "Desserts", "Drinks")

    var menuItems = if (searchPhrase.isNotEmpty()) {
        dishes.filter { it.title.contains(searchPhrase, ignoreCase = true) }
    } else {
        dishes
    }

    if (selectedCategory.isNotEmpty()) {
        menuItems = menuItems.filter { selectedCategory.contains(it.category) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                        contentDescription = stringResource(id = R.string.little_lemon_logo),
                        modifier = Modifier.height(30.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Profile.route) }) {
                        Image(
                            painter = painterResource(id = R.drawable.photo_profile),
                            contentDescription = "Profile",
                            //modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding, modifier = Modifier.background(Color.White)) {
            item {
                HeroSection(searchPhrase) { searchPhrase = it }
                MenuBreakdown(selectedCategory, categoryList)
            }
            items(menuItems) { dish ->
                MenuItem(navController, dish)
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    thickness = 1.dp,
                    color = LittleLemonColor.cloud
                )
            }
        }
    }
}

@Composable
fun HeroSection(searchPhrase: String, setSearchPhrase: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(color = LittleLemonColor.green)
            .padding(horizontal = 12.dp, vertical = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.restaurant_title),
            modifier = Modifier.height(60.dp),
            style = MaterialTheme.typography.displayLarge,
            color = LittleLemonColor.yellow,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Bottom) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .height(IntrinsicSize.Max),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.restaurant_location),
                    style = MaterialTheme.typography.displayMedium,
                    color = LittleLemonColor.cloud,
                )
                Text(
                    text = stringResource(id = R.string.restaurant_description),
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = LittleLemonColor.cloud,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = stringResource(id = R.string.hero_image),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .aspectRatio(5f / 6f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = setSearchPhrase,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = { Text(text = stringResource(id = R.string.enter_search_phrase)) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search Menu") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = LittleLemonColor.cloud,
                unfocusedContainerColor = LittleLemonColor.cloud,
                unfocusedLeadingIconColor = LittleLemonColor.charcoal,
                unfocusedPlaceholderColor = Color.Gray,
                focusedBorderColor = LittleLemonColor.cloud,
                focusedContainerColor = LittleLemonColor.cloud,
                focusedLeadingIconColor = LittleLemonColor.charcoal,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBreakdown(selectedCategory: SnapshotStateList<String>, categoryList: List<String>) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(
            text = stringResource(id = R.string.order_for_devilery),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )
        MultiChoiceSegmentedButtonRow(modifier = Modifier.padding(vertical = 16.dp), space = 10.dp) {
            categoryList.forEachIndexed { index, category ->
                SegmentedButton(
                    checked = category in selectedCategory,
                    onCheckedChange = {
                        if (category in selectedCategory) {
                            selectedCategory.remove(category)
                        } else {
                            selectedCategory.add(category)
                        }
                    },
                    shape = SegmentedButtonDefaults.shape(
                        position = index,
                        count = categoryList.size,
                    ),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = LittleLemonColor.green,
                        activeContentColor = LittleLemonColor.cloud
                    ),
                    icon = { /* No Icon */}
                ) {
                    val label = category.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                    Text(text = label)
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(navController: NavController, dish: MenuItemRoom) {
    ListItem(
        headlineContent = {
            Text(text = dish.title, style = MaterialTheme.typography.titleLarge)
        },
        modifier = Modifier.clickable {
            Log.d("AAA", "Click ${dish.id}")
            navController.navigate(DishDetails.route + "/${dish.id}")
        },
        supportingContent = {
            Column {
                Text(
                    text = dish.description,
                    modifier = Modifier.padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = LittleLemonColor.green,
                    maxLines = 2
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        },
        trailingContent = {
            GlideImage(
                model = dish.imageUrl,
                contentDescription = stringResource(id = R.string.dish_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(.25f)
                    .aspectRatio(1f / 1f),
            )
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HeroSection(searchPhrase = "", setSearchPhrase = {})
}