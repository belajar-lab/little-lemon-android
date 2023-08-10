package com.example.littlelemon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.DishDetails
import com.example.littlelemon.MenuItemRoom
import com.example.littlelemon.Profile
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Home(navController: NavController, dishes: List<MenuItemRoom>) {
    var searchPhrase by rememberSaveable { mutableStateOf("") }
    val selectedCategory = remember { mutableStateListOf<String>() }
    val categoryList = listOf("Starters", "Mains", "Desserts", "Drinks")

    var menuItems = if (searchPhrase.isNotEmpty()) {
        dishes.filter { it.title.contains(searchPhrase, ignoreCase = true) }
    } else {
        dishes
    }

    if (selectedCategory.isNotEmpty()) {
        menuItems = menuItems.filter { selectedCategory.contains(it.category) }
    }

    LazyColumn(modifier = Modifier.background(Color.White)) {
        item {
            Header(navController)
            HeroSection(searchPhrase) { searchPhrase = it }
            FilterMenu(selectedCategory, categoryList)
        }
        items(menuItems) { dish ->
            MenuItem2(navController, dish)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 12.dp),
                thickness = 1.dp,
                color = LittleLemonColor.cloud
            )
        }
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {

        }
        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(horizontal = 20.dp)
        )
        IconButton(onClick = { navController.navigate(Profile.route) }) {
            Image(
                painter = painterResource(id = R.drawable.photo_profile),
                contentDescription = "Cart",
                //modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun HeroSection(searchPhrase: String, setSearchPhrase: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(color = LittleLemonColor.green)
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.restaurant_title),
            style = MaterialTheme.typography.displayLarge,
            color = LittleLemonColor.yellow,
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(id = R.string.restaurant_location),
                    style = MaterialTheme.typography.displayMedium,
                    color = LittleLemonColor.cloud,
                )
                Text(
                    text = stringResource(id = R.string.restaurant_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = LittleLemonColor.cloud,
                    modifier = Modifier
                        .padding(bottom = 28.dp, end = 20.dp)
                        .fillMaxWidth(.6f)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = stringResource(id = R.string.hero_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f / 1f)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = setSearchPhrase,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(LittleLemonColor.cloud),
            placeholder = { Text(text = stringResource(id = R.string.enter_search_phrase)) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search Menu") },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterMenu(selectedCategory: SnapshotStateList<String>, categoryList: List<String>) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(
            text = stringResource(id = R.string.order_for_devilery),
            style = MaterialTheme.typography.headlineLarge
        )
        MultiChoiceSegmentedButtonRow {
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
                    shape = SegmentedButtonDefaults.shape(position = index, count = categoryList.size)
                ) {
                    Text(text = category)
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(navController: NavController? = null, dish: MenuItemRoom) {
    Surface(
        onClick = { navController?.navigate(DishDetails.route + "/${dish.id}") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.End) {
            Column(
                modifier = Modifier.fillMaxWidth(.75f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = dish.title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = dish.description,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    maxLines = 2
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
            GlideImage(
                model = dish.imageUrl,
                contentDescription = stringResource(id = R.string.dish_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f / 1f),
            )

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem2(navController: NavController, dish: MenuItemRoom) {
    ListItem(
        headlineContent = {
            Text(text = dish.title)
        },
        overlineContent = {
            Text(text = "$${dish.price}")
        },
        supportingContent = {
            Text(text = dish.description, maxLines = 2)
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

//@Preview
//@Composable
//fun FilterMenuPreview() {
//    FilterMenu()
//}