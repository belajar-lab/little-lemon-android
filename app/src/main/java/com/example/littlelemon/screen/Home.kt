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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun Home(navController: NavController, dishes: List<MenuItemRoom>) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        item {
            Header(navController)
            HeroSection()
        }
        items(dishes) { dish ->
            MenuItem(navController, dish)
            Divider(
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
fun HeroSection() {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }
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
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(LittleLemonColor.cloud),
            placeholder = { Text(text = stringResource(id = R.string.enter_search_phrase)) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search Menu") },
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

@Preview
@Composable
fun HomePreview() {
    HeroSection()
}